package br.dev.yann.rssreader.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import br.dev.yann.rssreader.annotation.AdminAuthRequired;
import br.dev.yann.rssreader.annotation.AuthRequired;
import br.dev.yann.rssreader.dto.UserDTO;
import br.dev.yann.rssreader.entity.User;
import br.dev.yann.rssreader.model.MessageResponse;
import br.dev.yann.rssreader.service.AuthAdminService;

@RequestScoped
@AuthRequired
@AdminAuthRequired
@Path("auth/admin")
public class AuthAdminController{

  private static Logger LOGGER = Logger.getLogger(AuthAdminController.class);


  @Inject
  private MessageResponse messageResponse;

  @Inject
  private AuthAdminService service;

  @GET
  @Path("findUsers")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response findAll(){
    List<User> users = service.findAllUsers();
    return Response.ok(users).build();
  }

  @GET
  @Path("findUsers/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findUserByIdAsAdmin(@PathParam("id") @Valid @Positive long id){
    var  user = service.findUserByIdAsAdmin(id);
    if(user == null){
      return Response.status(Status.NOT_FOUND).build();
    } else{
      return Response.ok(user).build();
    }

  }

  @PUT
  @Path("update")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response updateUserAsAdmin(@QueryParam("id") @Valid  @Positive long id, UserDTO.Request.Update user){

     if(user.getUsername() != null && service.hasUsernameWithOriginalId(user.getUsername(), id)) {
         return Response.status(Status.CONFLICT)
                      .entity(messageResponse.error("Username already exists"))
                      .build();
    }
    user.setId(id);

    if(service.updateUserAsAdmin(user)){
        return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND)
                      .entity(messageResponse.error("The id informed was not found"))
                      .build();
    }
  }

  @DELETE
  @Path("delete")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response deleteUserAsAdmin(@QueryParam("id") @Valid  @Positive long id){
    if(service.deleteUserAsAdmin(id)){
      LOGGER.info("deleted User with id "+id);
      return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND)
      .entity(messageResponse.error("The id informed was not found"))
      .build();
    }

  }

}

