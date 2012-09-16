package com.example.helloworld.resources;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.views.PersonView;
import com.yammer.dropwizard.auth.Auth;

@Path("/people/{name}")
@Produces(MediaType.TEXT_HTML)
public class PersonResource {

	private final PersonDAO dao;

	private final AtomicInteger id = new AtomicInteger();

	public PersonResource(PersonDAO dao) {
		this.dao = dao;
	}

	@PUT
	public void createPerson(@PathParam("name") String name, @QueryParam("password") String password) {
		dao.insert(id.getAndIncrement(), name, password);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public PersonView helloPerson(@PathParam("name") String name) {
		return new PersonView(dao.findByName(name));
	}

	/**
	 * Protected resource
	 * 
	 * @param user the authorized user
	 * @param name the name of the person to return
	 * @return person by name
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person byName(@Auth Person user, @PathParam("name") String name) {

		return name.equals(user.getName())
				? dao.findByName(name)
				: new Person(0, "Anonymous", "");
	}
}
