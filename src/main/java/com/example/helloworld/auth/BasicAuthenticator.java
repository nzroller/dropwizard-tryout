package com.example.helloworld.auth;

import javax.inject.Inject;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class BasicAuthenticator implements Authenticator<BasicCredentials, Person> {
	private final PersonDAO dao;

	@Inject
	BasicAuthenticator(PersonDAO dao) {
		this.dao = dao;
	}

	@Override
	public Optional<Person> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if ("secret".equals(credentials.getPassword())) {
			return Optional.of(dao.findByName(credentials.getUsername()));
		}
		return Optional.absent();
	}
}
