package br.dev.yann.rssreader.controller;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.URL;

import br.dev.yann.rssreader.annotation.AuthRequired;
import br.dev.yann.rssreader.service.RssService;
import br.dev.yann.rssreader.util.RssPagination;

@Path("rss")
@AuthRequired
@RequestScoped
public class RssController {

  private static final String HTTP_HTTPS = "^(http|https):\\/\\/.*";
  @Inject
  private RssService service;


  @GET
  @Path("findAll")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response findAllRssByUser(@HeaderParam("idToken") long id){
    Set<String> rss = service.findAll(id);
    return Response.ok(rss).build();
  }

  @DELETE
  @Path("delete")
  @Consumes(value = MediaType.APPLICATION_JSON)
  public Response deleteRss(@HeaderParam("idToken") long id, Set<@URL(regexp = HTTP_HTTPS) String> urlsSet){
     service.deleteRss(id, urlsSet);
    return Response.ok().build();
  }

  @DELETE
  @Path("deleteAll")
  @Consumes(value = MediaType.APPLICATION_JSON)
  public Response deleteAllRss(@HeaderParam("idToken") long id){
    service.deleteAllRss(id);
    return Response.ok().build();
  }
  @POST
  @Path("add")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response addRss(@HeaderParam("idToken") long id, @Valid Set<@URL(regexp = HTTP_HTTPS) String> urlsSet){
      service.addRss(id, urlsSet);
    return Response.ok().build();
  }

  @POST
  @Path("hasUrl")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response hasUrl(@HeaderParam("idToken") long id, @Valid List<@URL(regexp = HTTP_HTTPS) String> urlsList){
   List<String> rssList = service.getRssList(id, urlsList);
    return Response.ok(rssList).build();
  }

  @GET
  @Path("getUserContents")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response getContents(@HeaderParam("idToken") long id, @QueryParam("page")  int page, @QueryParam("size") @DefaultValue("10") int size, @QueryParam ("offset") int offset){
    RssPagination rssPage = service.getUserRssContents(id, page, size, offset);
    return Response.ok(rssPage).build();
  }

  @POST
  @Path("convertToRss")
  @Produces(value = MediaType.APPLICATION_JSON)
  @Consumes(value = MediaType.APPLICATION_JSON)
  public Response convertRssUrls(List<@URL(regexp = HTTP_HTTPS) String> urls, @QueryParam("page") int page, @QueryParam("size") @DefaultValue("10") int size, @QueryParam ("offset") int offset){

    RssPagination rssPage = service.convertRssUrls(urls, page, size, offset);

    return Response.ok(rssPage).build();
  }


}
