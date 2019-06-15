package com.wyiwei.commons.xstream;

public class RequestBean {
	
	private String name;
	public String age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "RequestBean [name=" + name + ", age=" + age + "]";
	}
	
	
}
