package br.dev.yann.rssreader.model.rss_elements.channel_elements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

  private String url;
  private String title;
  private String link;
  private String width;
  private String height;
  private String description;

}
