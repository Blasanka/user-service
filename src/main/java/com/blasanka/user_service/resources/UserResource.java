package com.blasanka.user_service.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.blasanka.user_service.controllers.UserController;
import com.blasanka.user_service.models.User;
import com.blasanka.user_service.resources.beans.UserFilterBean;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	UserController controller = new UserController();
	
	@GET
	public List<User> getUsers(@BeanParam UserFilterBean bean) {
		if (bean.getUsername() != null && bean.getUsername().equals("BLA")) {
			return controller.getUsers("user", bean.getId());
		} else {
			return new ArrayList<User>();
		}
	}
	
	@GET
	@Path("/{userId}")
	public User getUser(@PathParam("userId") long id) {
		return controller.getUser(id);
	}
	
	@POST
	public Response addUser(User user, @Context UriInfo info) {
		int count = controller.addUser(user);
		Status status = Status.CREATED;
		if (count == 0) status = Status.BAD_REQUEST;
		URI uri = info.getAbsolutePathBuilder().path(String.valueOf(count)).build();
		return Response.created(uri).status(status).entity(count).build();
	}

	@PUT
	@Path("/{userId}")
	public Response updateUser(@PathParam("userId") long id, User user) {
		user.setUserId(id);
		int count = controller.updateUser(user);
		Status status = Status.NO_CONTENT;
		if (count == 0) status = Status.BAD_REQUEST;
		return Response.status(status).entity(count).build();
	}
	
	@DELETE
	@Path("/{userId}")
	public Response deleteUser(@PathParam("userId") long id) {
		int count = controller.removeUser(id);
		Status status = Status.NO_CONTENT;
		if (count == 0) status = Status.BAD_REQUEST;
		return Response.status(status).entity(count).build();
	}
	
}
