package com.wyiwei.commons.threads.filereader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

public class LargeFileReader {
	
	private int threadSize;
	private int sliceCount;
	private String charset;
	private int bufferSize;
	private ReaderHandle<String> handle;
	private ExecutorService executorService;
	private long fileLength;
	private RandomAccessFile rAccessFile;
	private BlockingQueue<StartEndPair> startEndPairs;
	private CyclicBarrier cyclicBarrier;
	private AtomicLong counter = new AtomicLong(0L);
	
	// private boolean isSuccess = true;
	// private Throwable exception = null;
	
	public LargeFileReader(String fileName, ReaderHandle<String> handle, String charset, int bufferSize, int threadSize, int sliceCount) {
		File file = new File(fileName);
		this.fileLength = file.length();
		this.handle = handle;
		this.charset = charset;
		this.bufferSize = bufferSize;
		this.threadSize = threadSize;
		this.sliceCount = sliceCount;
		this.executorService = Executors.newFixedThreadPool(this.threadSize);
		this.startEndPairs = new LinkedBlockingDeque<LargeFileReader.StartEndPair>();
		
		try {
			rAccessFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		long everySize = this.fileLength / this.sliceCount;
		try {
			caculateStartEnd(0, everySize);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		cyclicBarrier = new CyclicBarrier(threadSize, new Runnable() {
			@Override
			public void run() {
				// endHandle();
				shutdown();
			}
		});
		for (int i=0; i<threadSize; i++) {
			executorService.execute(new SliceReaderTask(this.startEndPairs));
		}
	}
	
	public static BlockingQueue<String> readLargeFile(String fileName, int threadSize, int sliceCount) throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10000);
		class ReaderHandleImpl implements ReaderHandle<String>{
			public void handle(String line) {
				try {
					queue.put(line);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		LargeFileReader.Builder builder = new LargeFileReader.Builder(fileName, new ReaderHandleImpl());
		builder.withTreadSize(1).withSliceCount(10);
		LargeFileReader largefileReader = builder.build();
		largefileReader.start();
		return queue;
	}
	
	private class SliceReaderTask implements Runnable {
		
		private BlockingQueue<StartEndPair> startEndPairs;
		private byte[] readBuffer;
		private long sliceSize;
		private long start;
		
		public SliceReaderTask(BlockingQueue<StartEndPair> startEndPairs) {
			this.startEndPairs = startEndPairs;
			readBuffer = new byte[bufferSize];
		}
		
		@Override
		public void run() {
			StartEndPair startEndPair = null;
			while ((startEndPair = startEndPairs.poll()) != null) {
				this.start = startEndPair.start;
				sliceSize = startEndPair.end - startEndPair.start + 1;
				try {
					MappedByteBuffer mbBuffer = rAccessFile.getChannel().map(MapMode.READ_ONLY, this.start, sliceSize);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					for (int offset=0; offset<sliceSize; offset+=bufferSize ) {
						int length;
						if (offset + bufferSize > sliceSize) {
							length = (int)sliceSize - offset;
						} else {
							length = bufferSize;
						}
						
						mbBuffer.get(readBuffer, 0, length);
						for (int i=0; i<length; i++) {
							byte temp = readBuffer[i];
							if (temp == '\n' || temp == '\r') {
								handle(bos.toByteArray());
								bos.reset();
							} else {
								bos.write(temp);
							}
						}
					}
					if(bos.size()>0){
						handle(bos.toByteArray());
					}
					
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
			}
			
			try {
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
			
		}
	}
	
	private void handle(byte[] arr) throws UnsupportedEncodingException {
		String line = null;
		if (this.charset == null) {
			line = new String(arr);
		} else {
			line = new String(arr, this.charset);
		}
		if (line != null && !"".equals(line)) {
			this.handle.handle(line);
			counter.incrementAndGet();
		}
		
	}
	
	private void shutdown() {
		try {
			this.rAccessFile.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.executorService.shutdown();
		System.out.println("文件读完了");
	}
	
	private void caculateStartEnd(long start, long size) throws IOException {
		if (start > fileLength - 1) {
			return;
		}
		StartEndPair startEndPair = new StartEndPair();
		startEndPair.start = start;
		long endPosition = start + size - 1;
		if (endPosition >= fileLength - 1)
		{
			startEndPair.end = fileLength - 1;
			startEndPairs.add(startEndPair);
			return;
		}
		rAccessFile.seek(endPosition);
		byte temp = (byte)rAccessFile.read();
		while (temp != '\n' && temp != '\r') {
			endPosition++;
			if (endPosition >= fileLength - 1) {
				break;
			}
			rAccessFile.seek(endPosition);
			temp = (byte)rAccessFile.read();
		}
		startEndPair.end = endPosition;
		startEndPairs.add(startEndPair);
		
		caculateStartEnd(endPosition + 1, size);
		
	}
	
	public static class Builder {
		private int threadSize = 2;
		private int sliceCount = 1;
		private String charset = null;
		private int bufferedSize = 256*1024;
		private ReaderHandle<String> handle;
		private String fileName;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder(String fileName, ReaderHandle handle){
			this.fileName = fileName;
			this.handle = handle;
		}
		
		public Builder withTreadSize(int size){
			this.threadSize = size;
			return this;
		}
		
		public Builder withCharset(String charset){
			this.charset = charset;
			return this;
		}
		
		public Builder withSliceCount(int sliceCount){
			this.sliceCount = sliceCount;
			return this;
		}
		
		public LargeFileReader build(){
			return new LargeFileReader(this.fileName, this.handle, this.charset, this.bufferedSize, this.threadSize, this.sliceCount);
		}
		
	}
	
	private static class StartEndPair {
		
		public long start;
		public long end;
		
		public String toString() {
			return "start:" + start + ", end:" + end;
		}
		
//		@Override
//		public int hashCode(){
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + (int) (end ^ (end >>> 32));
//			result = prime * result + (int) (start ^ (start >>> 32));
//			return result;
//		}
//		
//		@Override
//		public boolean equals(Object obj){
//			if(this == obj)
//				return true;
//			if(obj == null)
//				return false;
//			if(getClass() != obj.getClass())
//				return false;
//			StartEndPair other = (StartEndPair) obj;
//			if(end != other.end)
//				return false;
//			if(start != other.start)
//				return false;
//			return true;
//		}
	}
}
