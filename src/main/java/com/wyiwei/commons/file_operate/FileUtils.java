package com.wyiwei.commons.file_operate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.wyiwei.commons.regex.RegexUtils;

public class FileUtils {

	public static void writeLine(File file, String line, String encoding, boolean append) throws IOException {
		List<String> list = new ArrayList<>();
		list.add(line);
		org.apache.commons.io.FileUtils.writeLines(file, encoding, list, append);
	}
	
	public static List<String> readLines(File file, String encoding) throws IOException {
		return org.apache.commons.io.FileUtils.readLines(file, Charset.forName(encoding));
	}
	
	public static String readFirstLine(File file, String encoding) throws IOException {
		return readLines(file, encoding).get(0);
	}
	
	public static void writeLines(File file, List<String> lines, String encoding, boolean append) throws IOException{
		org.apache.commons.io.FileUtils.writeLines(file, encoding, lines, append);
	}
	
	public static void copyFileUsingFileStreams(File source, File dest)
	        throws IOException {    
	    InputStream input = null;    
	    OutputStream output = null;    
	    try {
	           input = new FileInputStream(source);
	           output = new FileOutputStream(dest);        
	           byte[] buf = new byte[1024];        
	           int bytesRead;        
	           while ((bytesRead = input.read(buf)) > 0) {
	               output.write(buf, 0, bytesRead);
	           }
	    } finally {
	        input.close();
	        output.close();
	    }
	}
	
	/**
	 * get file by regex
	 * @param dirPath
	 * @param regex
	 * @return
	 */
	public static List<File> getFileByRegex(String dirPath, String regex){
		File[] files = new File(dirPath).listFiles();
		List<File> results = new ArrayList<>();
		if (files.length <= 0) {
			return results;
		}
		for (File f : files) {
			String fileName = f.getName();
			if (RegexUtils.matches(regex, fileName)) {
				results.add(f);
			}
		}
		return results;
	}
	
	/**
	 * 比对出两个文件夹下的不同文件
	 * @param path1
	 * @param path2
	 */
	public static void compareFileDeff(String path1, String path2) {
		File f1 = new File(path1);
		File f2 = new File(path2);
		List<String> sizeDiffList = new ArrayList<>();
		System.out.println("###############< "+path1+" >################");
		compareFileDiff2(f1.listFiles(), path2, sizeDiffList);
		System.out.println("###############< "+path2+" >################");
		sizeDiffList = new ArrayList<>();
		compareFileDiff2(f2.listFiles(), path1, sizeDiffList);
		System.out.println("###############< size diff >################");
		for (String diff : sizeDiffList) {
			System.out.println("size diff: " + diff);
		}
	}
	
	@SuppressWarnings("resource")
	public static void mergeFiles(String[] subFilesPath, String destFile) {
        RandomAccessFile raf = null;
        try {
            //申明随机读取文件RandomAccessFile
            raf = new RandomAccessFile(new File(destFile), "rw");
            //开始合并文件，对应切片的二进制文件
            for (String subFilePath : subFilesPath) {
                //读取切片文件
                RandomAccessFile reader = new RandomAccessFile(new File(subFilePath), "r");
                byte[] b = new byte[1024];
                int n = 0;
                //先读后写
                while ((n = reader.read(b)) != -1) {//读
                    raf.write(b, 0, n);//写
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static void splitFile(String srcFile, int splitCount) {
        RandomAccessFile raf = null;
        try {
            //获取目标文件 预分配文件所占的空间 在磁盘中创建一个指定大小的文件   r 是只读
            raf = new RandomAccessFile(new File(srcFile), "r");
            long length = raf.length();//文件的总长度
            long maxSize = length / splitCount;//文件切片后的长度
            long offSet = 0L;//初始化偏移量
            for (int i = 0; i < splitCount - 1; i++) { //最后一片单独处理
                long begin = offSet;
                long end = (i + 1) * maxSize;
                offSet = getWrite(srcFile, i, begin, end);
            }
            if (length - offSet > 0) {
                getWrite(srcFile, splitCount-1, offSet, length);
            }
 
        } catch (FileNotFoundException e) {
            System.out.println("没有找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	  * 指定文件每一份的边界，写入不同文件中
     * @param srcFileAbsPath 源文件
     * @param index 源文件的顺序标识
     * @param begin 开始指针的位置
     * @param end 结束指针的位置
     * @return long
     */
    public static long getWrite(String srcFileAbsPath, int index, long begin, long end){
        long endPointer = 0L;
        try {
        	File srcFile = new File(srcFileAbsPath);
        	String subFilePath = null;
        	boolean flag = false;
        	if (srcFile.getName().lastIndexOf(".")>0) {
        		flag = true;
        	}
            //申明文件切割后的文件磁盘
            RandomAccessFile in = new RandomAccessFile(new File(srcFileAbsPath), "r");
            if (flag) {
            	
            	subFilePath = srcFileAbsPath.substring(0, srcFileAbsPath.lastIndexOf("."))
            			+File.separator
            			+srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."))
            			+"_"+index
            			+srcFile.getName().substring(srcFile.getName().lastIndexOf("."), srcFile.getName().length());
            } else {
            	subFilePath = srcFileAbsPath
            			+File.separator
            			+srcFile.getName()
            			+"_"+index;
            }
            File subFileParent = new File(subFilePath).getParentFile();
            if (!subFileParent.exists()) {
            	subFileParent.mkdirs();
            }
            //定义一个可读，可写的文件并且后缀名为.tmp的二进制文件
            RandomAccessFile out = new RandomAccessFile(new File(subFilePath), "rw");
 
            //申明具体每一文件的字节数组
            byte[] b = new byte[1024];
            int n = 0;
            //从指定位置读取文件字节流
            in.seek(begin);
            //判断文件流读取的边界
            while(in.getFilePointer() <= end && (n = in.read(b)) != -1){
                //从指定每一份文件的范围，写入不同的文件
                out.write(b, 0, n);
            }
            //定义当前读取文件的指针
            endPointer = in.getFilePointer();
            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endPointer;
    }
	
	private static void compareFileDiff2(File[] files, String anotherAbsPath, List<String> sizeDiffList) {
		for (File file : files) {
			File anotFile = new File(anotherAbsPath + File.separator + file.getName());
			if (!anotFile.exists()) {
				System.out.println("private file or dir: "+ file.getPath());
				continue;
			} else if (file.isDirectory()) {
				compareFileDiff2(file.listFiles(), anotFile.getAbsolutePath(), sizeDiffList);
			} else {
				if (file.length() != anotFile.length()) {
					sizeDiffList.add(file.getAbsolutePath()+" vs "+anotFile.getAbsolutePath());
				}
			}
		}
	}
	
	public static void main(String[] args) {
//		compareFileDeff("C:\\Users\\hasee\\Desktop\\临时资料", "C:\\Users\\hasee\\Desktop\\临时资料2");
//		splitFile("C:\\Users\\Administrator\\Desktop\\20141104_115638.mp4", 3);
		String[] subFilesPath = {
			"C:\\Users\\Administrator\\Desktop\\20141104_115638\\20141104_115638_0.mp4",
			"C:\\Users\\Administrator\\Desktop\\20141104_115638\\20141104_115638_1.mp4",
			"C:\\Users\\Administrator\\Desktop\\20141104_115638\\20141104_115638_2.mp4"
		};
		String destFile = "C:\\Users\\Administrator\\Desktop\\20141104_115638.mp4";
		mergeFiles(subFilesPath, destFile);
		System.out.println("###############< done! >################");
	}
}
