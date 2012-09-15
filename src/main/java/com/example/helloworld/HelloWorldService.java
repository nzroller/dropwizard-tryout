package com.example.helloworld;

import com.example.helloworld.db.UserDAO;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.resources.UserResource;
import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.Service;
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
	}

	@Override
	protected void initialize(HelloWorldConfiguration configuration,
			Environment environment) throws ClassNotFoundException {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
		final String defaultAppendum = configuration.getDefaultAppendum();
		environment.addResource(new HelloWorldResource(template, defaultName, defaultAppendum));
		environment.addHealthCheck(new TemplateHealthCheck(template));

		final DatabaseFactory factory = new DatabaseFactory(environment);
		final Database db = factory.build(configuration.getDatabaseConfiguration(), "postgresql");
		final UserDAO dao = db.onDemand(UserDAO.class);
		environment.addResource(new UserResource(dao));
	}

}
