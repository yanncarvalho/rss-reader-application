package br.dev.yann.rssreader.filter;

import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.google.common.primitives.Longs;

import org.jboss.logging.Logger;

import br.dev.yann.rssreader.annotation.AdminAuthRequired;
import br.dev.yann.rssreader.model.MessageResponse;
import br.dev.yann.rssreader.service.AuthAdminService;


@Provider
@Priority(200)
@AdminAuthRequired
public class AuthAdminFilter implements ContainerRequestFilter {

  @Inject
  private MessageResponse messageResponse;

  @Inject
  private AuthAdminService service;

  private static Logger LOGGER = Logger.getLogger(AuthAdminFilter.class);

  @Override
  public void filter(ContainerRequestContext request){

    List<Long> adminsIds = service.findAllAdminsIds();

    //If there is no admins, the first user registered will be the admin
    if(adminsIds.isEmpty()){
      Long firstId = service.updateAndGetFirstId();
      LOGGER.info("New administrator registered: user with ID = "+firstId);
      adminsIds.add(firstId);
    }

    String id = request.getHeaderString("idToken");
    if (!adminsIds.contains(Longs.tryParse(id))) {
      LOGGER.warn("User with ID = "+id+" tried to acess unauthorized endpoint; in URI: "+ request.getUriInfo().getRequestUri() + " | REQUEST: " +request.getHeaders().toString());

      request.abortWith(Response.status(Status.UNAUTHORIZED)
          .entity(messageResponse.error("Administrative privileges required"))
          .build());
    }

  }

}
