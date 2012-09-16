package com.example.helloworld.db;

import java.util.HashMap;

import com.example.helloworld.core.Person;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.logging.Log;

public class PersonDAOStub implements PersonDAO {
	private static final Log LOG = Log.forClass(PersonDAO.class);

	private final HashMap<String, Person> hack;
	private final HashMap<Integer, Person> people;

	public PersonDAOStub() {
		people = new HashMap<>();
		hack = new HashMap<>();
	}

	@Override
	public void insert(int id, String name) {
		LOG.info("testing");
		people.put(id, new Person(id, name));
		hack.put(name, new Person(id, name));
	}

	@Override
	public String findNameById(int id) {
		return people.get(id).getName();
	}

	@Override
	public Person findByName(String name) {
		return hack.get(name);
	}

	@Override
	public ImmutableList<Person> findAll() {
		return ImmutableList
				.<Person> builder()
				.addAll(people.values())
				.build();
	}

	@Override
	public void createPersonTable() {
		// noop
	}
}
