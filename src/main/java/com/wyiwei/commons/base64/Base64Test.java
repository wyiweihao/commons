package com.wyiwei.commons.base64;

import java.io.UnsupportedEncodingException;

public class Base64Test {
	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println("11111111111111111111111110101110".length());
//		byte[] bytes = "王毅玮".getBytes("utf-8");
//		for (byte b : bytes) {
//			System.out.println(Integer.toBinaryString(b)+" ");;
////			System.out.print(b+ " ");
//		}
//		String s = "王毅玮";
//		int index = 0;
//		StringBuffer sb = new StringBuffer();
//		for (int i=0;i<bytes.length;i=i+2) 
//		{
//			String ss = null;
//			if (bytes.length-i < 2) {
//				byte[] bb = {bytes[i], 0};
//				ss = new String(bb);
//			} else {
//				byte[] bb = {bytes[i], bytes[i+1]};
//				ss = new String(bb);
//			}
//			sb.append(ss);
//		}
//		
//		System.out.println(Base64.encode(sb.toString()));
//		System.out.println(new String(bytes, "gbk"));
		
		
		System.out.println(Base64.encode("飞翔的企鹅".getBytes()));
		
		System.out.println(new String(Base64.decode("6aOe57+U55qE5LyB6bmF")));
		
		
		
	}
}
