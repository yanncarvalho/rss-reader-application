package br.dev.yann.rssreader.interceptor;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.dev.yann.rssreader.model.MessageResponse;


@Provider
public class ConstraintViolationInterceptor implements ExceptionMapper<ConstraintViolationException> {


  @Override
  public Response toResponse(ConstraintViolationException exception) {
      return Response.status(Status.BAD_REQUEST)
                     .entity(prepareMessage(exception))
                     .build();
  }

  private MessageResponse prepareMessage(ConstraintViolationException exception) {

    var messageResponse = new MessageResponse();
      for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
        String[] pathSplit = cv.getPropertyPath().toString().split("\\.");
        int len = pathSplit.length;
        String variableName = pathSplit[len-2].replaceAll("^\\w*", pathSplit[len-1]);
         messageResponse.validation(variableName+" "+cv.getMessage());
      }

      return messageResponse;
  }
}