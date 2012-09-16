package com.example.helloworld.views;

import com.example.helloworld.core.Person;
import com.yammer.dropwizard.views.View;

public class PersonView extends View {
	private final Person person;

	public PersonView(Person person) {
		super("/assets/person.mustache");
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}
}
