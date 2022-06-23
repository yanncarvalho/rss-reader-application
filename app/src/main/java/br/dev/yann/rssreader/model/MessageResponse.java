package br.dev.yann.rssreader.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(Include.NON_NULL)
public class MessageResponse {

  @JsonValue
  private Map<String,Object> messageElements;

  @JsonIgnore
  private int count = 1;

  public MessageResponse() {
    this.messageElements = new LinkedHashMap<>();
  }

  public MessageResponse addMessage (String name, Object o){
    messageElements.put(name, o);
    return this;
  }

  public MessageResponse error (String error){
    messageElements.put("error", error);
    return this;
  }

  public MessageResponse validation (String validation){
    messageElements.put("invalid arg #"+count++, validation);
    return this;
  }


}