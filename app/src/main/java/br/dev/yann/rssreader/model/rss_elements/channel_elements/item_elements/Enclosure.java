package br.dev.yann.rssreader.model.rss_elements.channel_elements.item_elements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Enclosure {

  @XmlAttribute
  private String url;

  @XmlAttribute
  private String length;

  @XmlAttribute
  private String type ;
}
