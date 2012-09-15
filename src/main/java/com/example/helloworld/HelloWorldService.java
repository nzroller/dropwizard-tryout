package com.example.helloworld;

import java.util.HashMap;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.resources.PersonResource;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.logging.Log;
import com.yammer.dropwizard.views.ViewBundle;

public class HelloWorldService extends Service<HelloWorldConfiguration> {
	private static final Log LOG = Log.forClass(HelloWorldService.class);

	public static void main(String[] args) throws Exception {
		new HelloWorldService().run(args);
	}

	private HelloWorldService() {
		super("hello-world");
		CacheBuilderSpec cacheSpec = AssetsBundle.DEFAULT_CACHE_SPEC;
		addBundle(new ViewBundle());
		addBundle(new AssetsBundle("/assets", cacheSpec, "/assets"));
	}

	@Override
	protected void initialize(HelloWorldConfiguration configuration,
			Environment environment) throws ClassNotFoundException {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
		final String defaultAppendum = configuration.getDefaultAppendum();
		environment.addResource(new HelloWorldResource(template, defaultName, defaultAppendum));
		environment.addHealthCheck(new TemplateHealthCheck(template));

		LOG.info("ENV {}", configuration.getEnvironment());
		if ("development".equals(configuration.getEnvironment())) {
			final HashMap<Integer, Person> people = new HashMap<>();
			final HashMap<String, Person> hack = new HashMap<>();
			environment.addResource(new PersonResource(new PersonDAO() {

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
			}));
		} else {
			final DatabaseFactory factory = new DatabaseFactory(environment);
			final Database db = factory.build(configuration.getDatabaseConfiguration(), "postgresql");
			environment.addResource(new PersonResource(db.onDemand(PersonDAO.class)));
		}

	}

}
