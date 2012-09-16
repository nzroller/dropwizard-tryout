package com.example.helloworld.core;

import org.codehaus.jackson.annotate.JsonProperty;

public class Person {
	final int id;
	@JsonProperty
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
