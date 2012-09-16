package com.example.helloworld.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.example.helloworld.core.Saying;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private final String template;
	private final String defaultName;
	private final String defaultAppendum;
	private final AtomicLong counter;

	@Inject
	HelloWorldResource(@Named("template") String template, 
			@Named("defaultName") String defaultName,
			@Named("defaultAppendum") String defaultAppendum) {
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
}
