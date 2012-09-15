package com.example.helloworld.core;

public class Saying {
	private final long id;
	private final String content;
	private final String appendum;

	public Saying(long id, String content, String appendum) {
		this.id = id;
		this.content = content;
		this.appendum = appendum;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getAppendum() {
		return appendum;
	}
}
