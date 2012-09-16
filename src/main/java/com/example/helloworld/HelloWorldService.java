package com.example.helloworld;

import com.fiestacabin.dropwizard.guice.AutoConfigService;
import com.google.common.cache.CacheBuilderSpec;
import com.google.inject.Injector;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class HelloWorldService extends AutoConfigService<HelloWorldConfiguration> {

	public static void main(String[] args) throws Exception {
		new HelloWorldService().run(args);
	}

	private HelloWorldService() {
		super("hello-world", "com.example.helloworld");
		CacheBuilderSpec cacheSpec = AssetsBundle.DEFAULT_CACHE_SPEC;
		addBundle(new ViewBundle());
		addBundle(new AssetsBundle("/assets", cacheSpec, "/"));
		addBundle(new AssetsBundle("/public", cacheSpec, "/public/"));
	}

	@Override
	protected void initializeWithInjector(HelloWorldConfiguration configuration,
			Environment environment,
			Injector injector) throws Exception {
		super.initializeWithInjector(configuration,
				environment,
				injector.createChildInjector(new HelloWorldModule(configuration,
						environment)));
	}

}
