package com.wyiwei.commons.hashcode_equals;

public class HashCodeBeanTest {
		
	public void test() {
		HashCodeBean bean = new HashCodeBean(0L, 100L);
		System.out.println(bean.hashCode());
		HashCodeBean bean2 = new HashCodeBean(0L, 100L);
		System.out.println(bean2.hashCode());
		System.out.println(bean2.toString());
	}
}