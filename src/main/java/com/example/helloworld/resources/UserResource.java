package com.example.helloworld.resources;

import javax.ws.rs.Path;

import com.example.helloworld.db.UserDAO;

@Path("/users")
public class UserResource {

	private final UserDAO dao;

	public UserResource(UserDAO dao) {
		this.dao = dao;
	}

}
