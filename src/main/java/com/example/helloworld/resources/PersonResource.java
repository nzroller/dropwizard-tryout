package com.example.helloworld.resources;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.views.PersonView;

@Path("/people/{name}")
@Produces(MediaType.TEXT_HTML)
public class PersonResource {

	private final PersonDAO dao;

	private final AtomicInteger id = new AtomicInteger();

	@Inject
	PersonResource(PersonDAO dao) {
		this.dao = dao;
	}

	@PUT
	public void createPerson(@PathParam("name") String name) {
		dao.insert(id.getAndIncrement(), name);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public PersonView helloPerson(@PathParam("name") String name) {
		return new PersonView(dao.findByName(name));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person byName(@PathParam("name") String name) {
		return dao.findByName(name);
	}

}
