package br.dev.yann.rssreader.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.dev.yann.rssreader.annotation.AuthRequired;
import br.dev.yann.rssreader.auth.JWTToken;
import br.dev.yann.rssreader.model.BucketToken;
import br.dev.yann.rssreader.model.MessageResponse;
import br.dev.yann.rssreader.service.AuthAnyUserService;
import br.dev.yann.rssreader.util.BucketTokenManage;

@Provider
@Priority(100)
@AuthRequired
public class AuthAnyUserFilter implements ContainerRequestFilter {

  private static Logger LOGGER = Logger.getLogger(AuthAnyUserFilter.class);

  @Inject
  private MessageResponse messageResponse;

  @Inject
  private AuthAnyUserService service;

  @Inject
  private JWTToken jwt;

  private final Pattern pattern = Pattern.compile("Bearer\\s\\w*.\\w*.\\w*");

  private static Map<Long,BucketToken> bucket = new HashMap<>();

  @Override
  public void filter(ContainerRequestContext request) {

    try {

      String authHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION);

      if (authHeader == null) {
        throw new NotAuthorizedException("Token required");
      }

      if (!pattern.matcher(authHeader).find()) {
        throw new NotAuthorizedException("Authentication token not valid");
      }

      String token = authHeader.substring("Bearer ".length());

      Map<String, Object> decode = jwt.decode(token);

      if (decode == null) {
        throw new NotAuthorizedException("Authentication bearer token not valid");
      }


      Long id = (Long) decode.get("usr");

      if (!service.hasUserById(id)) {
        throw new NotAuthorizedException("Authentication bearer token not valid");
      }

      if(!bucket.containsKey(id)){
        bucket.put(id, new BucketToken(id));
      } else {
        bucket.get(id).refleshRequestTime();
      }

      new BucketTokenManage(bucket).start();

      LOGGER.info("Authorized user with ID = "+ id + " | in URI: "+request.getUriInfo().getRequestUri()+" | REQUEST: "+request.getHeaders().toString()) ;

      request.getHeaders().putSingle("idToken", id.toString());

    } catch (NotAuthorizedException e) {
      LOGGER.info("Unauthorized request in URI: "+request.getUriInfo().getRequestUri()+" | REQUEST: "+request.getHeaders().toString()) ;
      request.abortWith(Response.status(Status.UNAUTHORIZED)
          .entity(messageResponse.error(e.getChallenges().get(0).toString()))
          .build());
    }
  }

}
