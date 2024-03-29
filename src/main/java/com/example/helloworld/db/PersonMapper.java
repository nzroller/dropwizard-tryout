package com.example.helloworld.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.example.helloworld.core.Person;

public class PersonMapper implements ResultSetMapper<Person> {

	@Override
	public Person map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		return new Person(rs.getInt("id"), rs.getString("name"), rs.getString("password"));
	}

}
