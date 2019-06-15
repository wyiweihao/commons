package com.wyiwei.commons;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;

public class JzTransferUtils {

	private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', 
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * byte[] to hex string
	 * @param bytes
	 * @return
	 */
	public static String bytesToHexStr(byte[] bytes) {
		// 一个byte为8位，可用两个十六进制位标识
		char[] buf = new char[bytes.length * 2];
		int a = 0;
		int index = 0;
		for(byte b : bytes) { // 使用除与取余进行转换
			if(b < 0) {
				a = 256 + b;
			} else {
				a = b;
			}

			buf[index++] = HEX_CHAR[a / 16];
			buf[index++] = HEX_CHAR[a % 16];
		}

		return new String(buf);
	}

	/**
	 * 十六进制转UTF8
	 * @param s
	 * @return
	 */
	public static String hexToStringGBK(String s) {
		byte[] baKeyword = hexToBytes(s);
		try {
			s = new String(baKeyword, "UTF-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
			return "";
		}
		return s;
	}

	/**
	 * 十六进制转十进制int
	 * @param s
	 * @return
	 */
	public static int hexToInt(String s) {
		byte[] baKeyword = hexToBytes(s);
		byte[] bytes = new byte[4];
		int value = 0;
		if (baKeyword.length == 1) {
			bytes[0] = 0x00;
			bytes[1] = 0x01;
			bytes[2] = 0x02;
			bytes[3] = baKeyword[0];
		} else if (baKeyword.length == 2) {
			bytes[0] = 0x00;
			bytes[1] = 0x01;
			bytes[2] = baKeyword[0];
			bytes[3] = baKeyword[1];
		} else if (baKeyword.length == 3) {
			bytes[0] = 0x00;
			bytes[1] = baKeyword[0];
			bytes[2] = baKeyword[1];
			bytes[3] = baKeyword[2];
		}
		// 由高位到低位  
		for (int i = 0; i < 4; i++) {  
			int shift = (4 - 1 - i) * 8;  
			value += (baKeyword[i] & 0x000000FF) << shift;// 往高位游  
		}  
		return value;
	}

	/** 
	 * 10进制转16进制 
	 * @param i 
	 * @return 
	 */  
	public static byte[] intToByteArray(int num) {  
		byte[] result = new byte[4];  
		// 由高位到低位  
		result[0] = (byte) ((num >> 24) & 0xFF);  
		result[1] = (byte) ((num >> 16) & 0xFF);  
		result[2] = (byte) ((num >> 8) & 0xFF);  
		result[3] = (byte) (num & 0xFF);  
		return result;  
	}

	/**
	 * 十六进制字符串转byte[]
	 * @param s
	 * @return
	 */
	public static byte[] hexToBytes(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return baKeyword;
	}

	/**
	 * 字符串转换为16进制字符串
	 * @param str
	 * @param charsetName
	 * @return
	 */
	public static String str2hexStr(String str, String charsetName) {
		byte[] bytes = new byte[0];
		String hexString = "0123456789abcdef";
		// 使用给定的 charset 将此 String 编码到 byte 序列
		if (StringUtils.isNotBlank(charsetName)) {
			try {
				bytes = str.getBytes(charsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			bytes = str.getBytes();
		}
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f)));
		}
		return sb.toString();
	}

	/**
	 * 异或运算
	 * @param data
	 * @return
	 */
	public static byte getXor(byte[] data){
	       byte temp = data[0];
	       for(int i=1;i<data.length;i++){
	          temp^=data[i];
	       }
	       return temp;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "王江波1234是一个小朋友，HOHOHOHO";
		String hex = str2hexStr(str, "utf-8");
		System.out.println(hex);
		byte[] bytes = hexToBytes(hex);
		System.out.println(str);
		System.out.println(bytes);
		System.out.println(bytesToHexStr(bytes));
		System.out.println(new String(bytes, Charset.forName("utf-8")));
	}
}
