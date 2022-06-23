package br.dev.yann.rssreader.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.dev.yann.rssreader.model.rss_elements.Channel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"originalLink", "content"})
public class Rss {

  @XmlTransient
  private String originalLink;

  @JsonProperty ("content")
  private Channel channel;

}
