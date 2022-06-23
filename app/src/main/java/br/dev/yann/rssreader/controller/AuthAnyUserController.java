package br.dev.yann.rssreader.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import br.dev.yann.rssreader.annotation.AuthRequired;
import br.dev.yann.rssreader.annotation.JWTSerialization;
import br.dev.yann.rssreader.dto.UserDTO;
import br.dev.yann.rssreader.entity.User;
import br.dev.yann.rssreader.model.MessageResponse;
import br.dev.yann.rssreader.service.AuthAnyUserService;

@Path("auth")
@RequestScoped
public class AuthAnyUserController {

  private static Logger LOGGER = Logger.getLogger(AuthAnyUserController.class);

  @Inject
  private MessageResponse messageResponse;

  @Inject
  private AuthAnyUserService service;

  @GET
  @Path("find")
  @AuthRequired
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response find(@HeaderParam("idToken") long id) {

    return Response.status(Status.OK)
                  .entity(service.findByIdResponse(id))
                  .build();
  }

  @POST
  @Path("login")
  @JWTSerialization
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response login(@Valid UserDTO.Request.Login user, @Context HttpHeaders request, @Context UriInfo uriInfo) {
    User userNew = service.login(user);
    if (userNew != null) {
      return Response.status(Status.OK)
          .entity(userNew)
          .build();
    } else {

      LOGGER.info("Unauthorized request in URI: "+uriInfo.getRequestUri()+" | REQUEST: "+request.getRequestHeaders());
      return Response.status(Status.UNAUTHORIZED)
          .entity(messageResponse.error("Authentication is not valid"))
          .build();
    }

  }

  @POST
  @Path("save")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response save( @Valid  UserDTO.Request.Save user) throws Exception {
    if (service.hasUsername(user.getUsername())) {
      return Response.status(Status.CONFLICT)
          .entity(messageResponse.error("Username already exists"))
          .build();
    } else {
      User newUser = new User(user.getName(), user.getPassword(), user.getUsername());
      service.save(newUser);
      LOGGER.info("Saved new user");
      return Response.status(Status.CREATED).build();
    }
  }

  @DELETE
  @Path("delete")
  @AuthRequired
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response delete(@HeaderParam("idToken") long id) {
    service.delete(id);
    LOGGER.info("deleted User with ID = "+id);
    return Response.status(Status.OK).build();
  }

  @PUT
  @Path("update")
  @AuthRequired
  @JWTSerialization
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response updade(@HeaderParam("idToken") long id, UserDTO.Request.Update user) {
    if(user.getUsername() != null && service.hasUsernameWithOriginalId(user.getUsername(), id)) {
      return Response.status(Status.CONFLICT)
          .entity(messageResponse.error("Username already exists"))
          .build();
    } else {
      user.setId(id);
      User answerUser = service.update(user);
      return Response.status(Status.OK)
          .entity(answerUser)
          .build();
    }
  }

}
