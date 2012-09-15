package com.example.helloworld.resources;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.yammer.dropwizard.views.View;

@Path("/people/{name}")
@Produces(MediaType.TEXT_HTML)
public class PersonResource {

	private final PersonDAO dao;

	private final AtomicInteger id = new AtomicInteger();

	public PersonResource(PersonDAO dao) {
		this.dao = dao;
	}

	@PUT
	public void createPerson(@PathParam("name") String name) {
		dao.insert(id.getAndIncrement(), name);
	}

	@GET
	public PersonView helloPerson(@PathParam("name") String name) {
		return new PersonView(dao.findByName(name));
	}

	public static class PersonView extends View {
		private final Person person;

		public PersonView(Person person) {
			super("/person.ftl");
			this.person = person;
		}

		public Person getPerson() {
			return person;
		}
	}

}
