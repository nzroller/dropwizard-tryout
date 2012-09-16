package com.example.helloworld;

import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.db.PersonDAOStub;
import com.example.helloworld.health.TemplateHealthCheck;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.logging.Log;

/**
 * Module for binding hello world services
 */
public class HelloWorldModule extends AbstractModule {

	private static final Log LOG = Log.forClass(HelloWorldModule.class);

	private final HelloWorldConfiguration configuration;

	private final Environment environment;

	HelloWorldModule(HelloWorldConfiguration configuration, Environment environment) {
		super();
		this.configuration = configuration;
		this.environment = environment;
	}

	@Override
	protected void configure() {
		bindConstant().annotatedWith(Names.named("template")).to(configuration.getTemplate());
		bindConstant().annotatedWith(Names.named("defaultName")).to(configuration.getDefaultName());
		bindConstant().annotatedWith(Names.named("defaultAppendum")).to(configuration.getDefaultAppendum());

		LOG.info("ENV {}", configuration.getEnvironment());
		if ("development".equals(configuration.getEnvironment())) {
			bind(PersonDAO.class).toInstance(new PersonDAOStub());
		} else {
			final DatabaseFactory factory = new DatabaseFactory(environment);
			Database db;
			try {
				db = factory.build(configuration.getDatabaseConfiguration(), "postgresql");
				bind(PersonDAO.class).toInstance(db.onDemand(PersonDAO.class));
			} catch (ClassNotFoundException e) {
				addError(e);
			}
		}

	}

}
