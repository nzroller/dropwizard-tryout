package com.example.helloworld.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.example.helloworld.core.Person;
import com.google.common.collect.ImmutableList;

@RegisterMapper(PersonMapper.class)
public interface PersonDAO {

	@SqlUpdate("create table person(id int primary key, name varchar(100))")
	void createPersonTable();

	@SqlUpdate("insert into person (id, name, password) values (:id, :name, :password)")
	void insert(@Bind("id") int id, @Bind("name") String name, String password);

	@SqlQuery("select name from person where id = :id")
	String findNameById(@Bind("id") int id);

	@SqlQuery("select id, name, password from person")
	ImmutableList<Person> findAll();
	
	@SqlQuery("select id, name, password from person where name = :name")
	Person findByName(@Bind("name") String name);
}
