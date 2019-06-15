package com.wyiwei.commons.concat;

/**
 * 实验证明StringBuilder的字符串拼接效率要高
 * @author hasee
 *
 */
public class StringConcat {
	
	public void test() {
		stringConcat();
		stringBuilder();
	}
	
	public void stringConcat() {
		long start = System.currentTimeMillis();
		String s = "";
		for (int i=1; i<1000000; i++) {
			s.concat(1+"").concat("\n");
		}
		long end = System.currentTimeMillis();
		System.out.println("stringConcat耗时："+(end-start)+"毫秒");
	}
	
	public void stringBuilder() {
		long start = System.currentTimeMillis();
		StringBuilder s = new StringBuilder();
		for (int i=1; i<1000000; i++) {
			s.append(1).append("\n");
		}
		long end = System.currentTimeMillis();
		System.out.println("stringBuilder耗时："+(end-start)+"毫秒");
	}
}
