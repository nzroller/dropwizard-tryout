package com.example.helloworld.core;

public class Person {
	final int id;
	final String name;

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}

}
