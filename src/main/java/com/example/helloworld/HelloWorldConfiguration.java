package com.example.helloworld;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

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

	@NotEmpty
	@JsonProperty
	private String environment = "production"; 
	
	@Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }

	
	public String getTemplate() {
		return template;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public String getDefaultAppendum() {
		return defaultAppendum;
	}

	public String getEnvironment() {
		return environment;
	}
}
