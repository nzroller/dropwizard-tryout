package com.example.helloworld;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.health.TemplateHealthCheck;
import com.google.common.cache.CacheBuilderSpec;

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
			Environment environment) {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
		final String defaultAppendum = configuration.getDefaultAppendum();
		environment.addResource(new HelloWorldResource(template, defaultName, defaultAppendum));
		environment.addHealthCheck(new TemplateHealthCheck(template));
		
	}

}
