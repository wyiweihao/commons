package com.wyiwei.commons.threads.filereader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;


public class LargeFileReaderTest {
	
	private static String fileName = "C:\\Users\\Administrator\\Desktop\\testfile.txt";
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
//		createFile();
		testLargeFileReader();
//		testFileUtils();
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start) + "毫秒");
	}

	
	public static void testLargeFileReader() throws InterruptedException {
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
		builder.withTreadSize(3).withSliceCount(10);
		LargeFileReader largefileReader = builder.build();
		long start = System.currentTimeMillis();
		largefileReader.start();
		System.out.println("调用读文件成功");
		int i=0;
		while (true) {
			String line = queue.poll(1000, TimeUnit.MILLISECONDS);
			if (line == null) {
				break;
			} else {
				i++;
//				System.out.println(line);
			}
		}
		System.out.println(i);
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start) + "毫秒");
	}
	
	public static void testFileUtils() throws IOException {
		long start = System.currentTimeMillis();
		List<String> lines = FileUtils.readLines(new File(fileName), "UTF-8");
		for (@SuppressWarnings("unused") String line : lines) {
			line = null;
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start) + "毫秒");
	}
	
	public static void createFile() throws IOException {
		List<String> list = new ArrayList<>();
		for (int i=1; i<=10000000; i++) {
			list.add("nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, "
					+ "nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, nnnnnnnnnn, ");
		}
		
		String fileName = "C:\\Users\\Administrator\\Desktop\\testfile.txt";
		FileUtils.writeLines(new File(fileName), "utf-8", list);
	}
}
