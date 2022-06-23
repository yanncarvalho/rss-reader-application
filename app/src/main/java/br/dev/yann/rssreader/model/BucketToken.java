package br.dev.yann.rssreader.model;

import java.time.Duration;
import java.time.Instant;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import br.dev.yann.rssreader.interceptor.ExceptionInterceptor;

public class BucketToken {

  private final short MAX_REQUEST_PER_MIN = 50;

  private final long DURATION_IN_MIN = 1L;

  private final long userId;

  private Instant requestTime = Instant.now();

  private short requestNumber = 0;

  private static Logger LOGGER = Logger.getLogger(ExceptionInterceptor.class);

  public BucketToken(long userId) {
    this.userId = userId;
  }

  public void refleshRequestTime(){

    if(Duration.between(requestTime, Instant.now()).toMinutes() >= DURATION_IN_MIN){
      requestNumber = 0;
    } else{
      requestNumber++;
    }

    if(requestNumber >= MAX_REQUEST_PER_MIN){
      LOGGER.fatal("User with ID = "+userId+ " had too many requests (HTTP 409) | LAST REQUEST: "+this.requestTime +" | RATE LIMIT: " + MAX_REQUEST_PER_MIN +" per minute");
      throw new ClientErrorException(Status.TOO_MANY_REQUESTS);
    }

  }

  public boolean isRemovable(){
    return (Duration.between(requestTime, Instant.now()).toMinutes() >= DURATION_IN_MIN);
  }


}
