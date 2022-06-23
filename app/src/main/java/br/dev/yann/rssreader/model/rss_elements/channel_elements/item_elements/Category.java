package br.dev.yann.rssreader.model.rss_elements.channel_elements.item_elements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {

  @XmlAttribute
  private String domain;

  @XmlElement
  private String category;

}
