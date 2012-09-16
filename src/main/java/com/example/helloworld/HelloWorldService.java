package com.example.helloworld;

import com.example.helloworld.auth.BasicAuthenticator;
import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.db.PersonDAOStub;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.resources.PersonResource;
import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.views.ViewBundle;

public class HelloWorldService extends Service<HelloWorldConfiguration> {
	public static void main(String[] args) throws Exception {
		new HelloWorldService().run(args);
	}

	private HelloWorldService() {
		super("hello-world");
		CacheBuilderSpec cacheSpec = AssetsBundle.DEFAULT_CACHE_SPEC;
		addBundle(new ViewBundle());
		addBundle(new AssetsBundle("/assets", cacheSpec, "/"));
		addBundle(new AssetsBundle("/public", cacheSpec, "/public/"));
	}

	@Override
	protected void initialize(HelloWorldConfiguration configuration, Environment environment) throws Exception {
		PersonDAO personDao = buildPersonDAO(configuration, environment);
		environment.addProvider(new BasicAuthProvider<Person>(new BasicAuthenticator(personDao), "realm"));
		environment.addResource(new PersonResource(personDao));
		environment.addResource(new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName(),
				configuration.getDefaultAppendum()));
		environment.addHealthCheck(new TemplateHealthCheck(configuration.getTemplate()));
	}

	private PersonDAO buildPersonDAO(HelloWorldConfiguration configuration, Environment environment)
			throws ClassNotFoundException {
		if ("development".equals(configuration.getEnvironment())) {
			return new PersonDAOStub();
		} else {
			final DatabaseFactory factory = new DatabaseFactory(environment);
			Database db = factory.build(configuration.getDatabaseConfiguration(), "postgresql");
			return db.onDemand(PersonDAO.class);
		}
	}

}
