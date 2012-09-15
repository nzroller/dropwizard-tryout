package com.example.helloworld;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class HelloWorldConfiguration extends Configuration {
	@NotEmpty
	@JsonProperty
	private String template;

	@NotEmpty
	@JsonProperty
	private String defaultName = "Stranger";

	@NotEmpty
	@JsonProperty
	private String defaultAppendum = "Appendum";

	public String getTemplate() {
		return template;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public String getDefaultAppendum() {
		return defaultAppendum;
	}
}
