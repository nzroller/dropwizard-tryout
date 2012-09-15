package com.example.helloworld.resources;

import com.example.helloworld.core.Person;
import com.example.helloworld.core.Saying;
import com.google.common.base.Optional;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private final String template;
	private final String defaultName;
	private final String defaultAppendum;
	private final AtomicLong counter;

	public HelloWorldResource(String template, String defaultName, String defaultAppendum) {
		this.template = template;
		this.defaultName = defaultName;
		this.counter = new AtomicLong();
		this.defaultAppendum = defaultAppendum;
	}

	@GET
	@Timed
	public Saying sayHello(@QueryParam("name") Optional<String> name, @QueryParam("appendum") Optional<String> appendum) {
		return new Saying(counter.incrementAndGet(),
				String.format(template, name.or(defaultName)), appendum.or(defaultAppendum));
	
	}
	
	
	@GET
	@Path("person")
	public PersonView helloPerson(@QueryParam("salutation") Optional<String> salutation) {
		return new PersonView(new Person("Tim", "Roller"));
	}
	
	public static class PersonView extends View {
	    private final Person person;

	    public PersonView(Person person) {
	        super("person.ftl");
	        this.person = person;
	    }

	    public Person getPerson() {
	        return person;
	    }
	}
}
