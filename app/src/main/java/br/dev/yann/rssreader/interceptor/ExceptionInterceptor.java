package br.dev.yann.rssreader.interceptor;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.dev.yann.rssreader.model.MessageResponse;


@Provider
public class ExceptionInterceptor implements ExceptionMapper<Exception> {

  private static Logger LOGGER = Logger.getLogger(ExceptionInterceptor.class);
  @Inject
  private MessageResponse messageResponse;

  public Response toResponse(Exception exception) {

    if (exception instanceof NotFoundException) {

        return Response.status(Status.NOT_FOUND)
            .entity(messageResponse.error("Path not found"))
            .build();

      }
      if (exception instanceof ClientErrorException) {
        return Response.status(Status.TOO_MANY_REQUESTS).build();
      } else {

        LOGGER.error("An exception was thrown", exception);
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(this.messageResponse.error("Failure while processing"))
            .build();
      }

    }
 }
