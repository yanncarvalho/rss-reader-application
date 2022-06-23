package br.dev.yann.rssreader.model.rss_elements.channel_elements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TextInput {
  private String title;
  private String description;
  private String name;
  private String link;

}
