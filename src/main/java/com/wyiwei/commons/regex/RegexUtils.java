package com.wyiwei.commons.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	/**
	 * 最简单用法
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean matches(String regex, String str) {
		if (regex == null || str == null) {
			return false;
		}
		return Pattern.matches(regex, str);
	}
	
	public void matchesTest() {
		System.out.println(matches("^\\w+@\\d{3}\\.com$", "wangyiweiwell@163.com"));
	}
	/**
	 * 找出符合正则的片段及其位置
	 * @param line
	 * @return
	 */
	public static void statisticsWords(String line) {
		 Pattern pattern = Pattern.compile("[a-zA-Z]+");
		 Matcher matcher = pattern.matcher(line);
		 while (matcher.find()) {
			 System.out.println(matcher.group()+" 位置：["+matcher.start()+", "+matcher.end()+"]");
		}
		/* matcher.matches(); // 尝试将整个区域与模式匹配，所以这里是false
		 * matcher.groupCount();// 这个是只有matcher.matches()匹配成功了才有数值
		 */
	}
	
	public void statisticsWordsTest() {
		statisticsWords("ni hao, 我是Jones, who are you?");
	}
	
	/**
	 * 定长字段分割
	 * @param line
	 * @return
	 */
	public List<String> splitLine(String line){
		Pattern pattern = Pattern.compile("^(.{1})(.{2})(.{3})(.{4}).*$");
		Matcher matcher = pattern.matcher(line);
		List<String> fieldsList = new ArrayList<>();
		if (matcher != null && matcher.matches()) {
			for (int i=0;i<=matcher.groupCount();i++) {// 当i=0时，匹配的是整个字符串，算第一个组，其他只有加了括号的才算一个组
				fieldsList.add(matcher.group(i));
			}
		}
		return fieldsList;
	}
	
	public void splitLineTest(){
		System.out.println(splitLine("abCdEfgHij123"));
	}
	
	/**
	 * Pattern.quote(String s)方法测试，该方法返回正则表达式字符串s的字面量，如下：
	 */
	public void quoteTest() {
		String regex = Pattern.quote(".*");
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher("123");
		boolean match = matcher.matches();// false
		System.out.println(match);
		matcher = pattern.matcher("foo");// false
		System.out.println(match);
		matcher = pattern.matcher(".*");
		match = matcher.matches();// true
		System.out.println(match);
	}
	
	/**
	 * 按照指定的模式分割字符串
	 * @param regex
	 * @param s
	 * @return
	 */
	public static String[] split(String regex, String s) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.split(s);
	}
	
	public void splitTest() {
		System.out.println(split("\\d{2,3}", "abcd4efghij1klmnopq456rstuvw26xyz"));
	}
	
}
















