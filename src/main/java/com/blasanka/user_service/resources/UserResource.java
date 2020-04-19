package com.blasanka.user_service.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import com.blasanka.user_service.exceptions.UnAuthorizedException;
import com.blasanka.user_service.models.User;
import com.blasanka.user_service.models.LoginResult;
import com.blasanka.user_service.models.LoginSession;
import com.blasanka.user_service.resources.beans.UserFilterBean;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	UserController controller = new UserController();
	
	@GET
	public List<User> getUsers(@BeanParam UserFilterBean bean) {
		if (controller.validateToken(bean.getxAuthorization())) {
			return controller.getUsers("user", bean.getId());
		} else {
//			return new ArrayList<User>();
			throw new UnAuthorizedException("Authentication failed");
		}
	}
	
	@GET
	@Path("/{userId}")
	public Response getUser(@PathParam("userId") long id, @BeanParam UserFilterBean bean) {
		if (controller.validateToken(bean.getxAuthorization())) {
			User user = controller.getUser(id);
			if (user.getUserId() == 0) throw new BadRequestException();
			return Response.status(Status.CREATED).entity(user).build();
		} else {
			throw new UnAuthorizedException("Authentication failed");
//			return Response.status(Status.UNAUTHORIZED).entity(null).build();
		}
	}
	
	@POST
	public Response addUser(User user, @Context UriInfo info, @BeanParam UserFilterBean bean) {
		user.setRoleId(bean.getRoleId());
		int count = controller.addUser(user);
		if (count == 0) throw new BadRequestException();
		URI uri = info.getAbsolutePathBuilder().path(String.valueOf(count)).build();
		return Response.created(uri).status(Status.CREATED).entity(count).build();
	}
	
	@PUT
	@Path("/{userId}")
	public Response updateUser(@PathParam("userId") long id, @HeaderParam("x-authorization") String xAuth, User user) {
		if (controller.validateToken(xAuth)) {
			user.setUserId(id);
			int count = controller.updateUser(user);
			if (count == 0) throw new BadRequestException();
			return Response.status(Status.NO_CONTENT).entity(count).build();
		} else {
			throw new UnAuthorizedException("Authentication failed");
		}
	}
	
	@DELETE
	@Path("/{userId}")
	public Response deleteUser(@PathParam("userId") long id, @HeaderParam("x-authorization") String xAuth) {
		if (controller.validateToken(xAuth)) {
			int count = controller.removeUser(id);
			if (count == 0) throw new BadRequestException();
			return Response.status(Status.NO_CONTENT).entity(count).build();
		} else {
			throw new UnAuthorizedException("Authentication failed");
		}
	}

	
	@POST
	@Path("/login")
	public Response loginUser(User user, @Context UriInfo info) {
		LoginResult result = controller.getUserByCredentials(user.getEmail(), user.getPassword());
		
		if (result.getErrors().size() > 0) {
			return Response.status(Status.UNAUTHORIZED).entity(result.getErrors()).build();
		} else {
			LoginSession ls = result.getLoginSession();
			URI uri = info.getAbsolutePathBuilder().path(String.valueOf(ls.getUser().getUserId())).build();
			return Response.created(uri).status(Status.OK).entity(ls).build();
		}
	}

}
