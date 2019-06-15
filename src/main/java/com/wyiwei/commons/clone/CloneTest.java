package com.wyiwei.commons.clone;

/**
 * 拷贝测试
 * @author hasee
 *
 */
public class CloneTest {
	
	/**
	 * 感觉拷贝是鸡肋，推荐使用序列化实现对象的拷贝
	 * @param args
	 */
	public static void main(String[] args) {
		// 定义父亲
		Person father = new Person("父亲");
		// 定义大儿子
		Person son1 = new Person("大儿子", father);
		// 小儿子由大儿子克隆来
		Person son2 = son1.clone();
		son2.setName("小儿子");
		// 大儿子认了个干爹
		son1.getParant().setName("干爹");// 对象的属性拷贝出来的是引用，基本类型是具体值 ，这里更改了大儿子的父请，小儿子的父亲也跟着改变
		System.out.println(son1);
		System.out.println(son2);
	}
}
