package com.wyiwei.commons.clone;

import org.apache.commons.lang.SerializationUtils;

/**
 * 实践证明流方式克隆比new出来的要慢
 * @author hasee
 *
 */
public class SerializationCloneTest {
	
	private static int NUMS = 1000000;
	
	public static void main(String[] args) {
		clone2();
		clone4();
		clone3();
	}
	
	public static void clone1() {
		Person father = new Person("父亲");
		Person son1 = new Person("大儿子", father);
		Person son2 = CloneUtils.clone(son1);
		son2.setName("小儿子");
		son1.getParant().setName("干爹");
		
		System.out.println(son1);
		System.out.println(son2);
	}
	
	/**
	 * 性能测试
	 */
	public static void clone2() {
		Person father = new Person("父亲");
		Person son1 = new Person("大儿子", father);
		
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUMS; i++) {
			Person son2 = CloneUtils.clone(son1);
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son1.getParant().setName("干爹");
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("字节流克隆方式耗时：" + (endTime - startTime) + "毫秒");
	}
	
	/**
	 * 性能测试
	 */
	public static void clone3() {
		Person father = new Person("父亲");
		Person son1 = new Person("大儿子", father);
		
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUMS; i++) {
			Person son2 = new Person("小儿子", father);
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son1.getParant().setName("干爹");
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("new克隆方式耗时：" + (endTime - startTime) + "毫秒");
	}
	
	/**
	 * 性能测试
	 */
	public static void clone4() {
		Person father = new Person("父亲");
		Person son1 = new Person("大儿子", father);
		
		long startTime = System.currentTimeMillis();
		for (int i=0; i<NUMS; i++) {
			Person son2 = (Person)SerializationUtils.clone(son1);
			son2.setParant(father);
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son2.setName("小儿子");
			son1.getParant().setName("干爹");
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("apache工具类克隆方式耗时：" + (endTime - startTime) + "毫秒");
	}
	
	
	
}
