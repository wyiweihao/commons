package com.wyiwei.commons.clone;

import java.io.Serializable;

public class Person implements Cloneable, Serializable{
	
	private static final long serialVersionUID = -1255470512759696807L;

	private String name;
	
	private Person parant;
	
	public Person(String name) {
		this.name = name;
	}
	
	public Person(String name, Person parant) {
		this.name = name;
		this.parant = parant;
	}

	@Override
	public Person clone() {
		Person p = null;
		try {
			p = (Person)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return p;	
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getParant() {
		return parant;
	}

	public void setParant(Person parant) {
		this.parant = parant;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", parant=" + parant.getName() + "]";
	}
	
}
