package com.wyiwei.commons.comm.so;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Person {
	
	private String name;
	private Integer age;
	private Date birthDay;
	private String nationality;
	private Set<String> members = new HashSet<>();
	
	public Person() {}
	
	public Person(String name, Date birthDay, String nationality) {
		this.name = name;
		this.birthDay = birthDay;
		this.nationality = nationality;
	}
	
	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
	
	public boolean isMember(String name) {
		return this.members.contains(name)? true : false;
	}
	
	public boolean addMember(String name) {
		return this.members.add(name)? true : false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Set<String> getMembers() {
		return members;
	}

	public void setMembers(Set<String> members) {
		this.members = members;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", birthDay=" + birthDay + ", nationality=" + nationality
				+ ", members=" + members + "]";
	}

	
}
