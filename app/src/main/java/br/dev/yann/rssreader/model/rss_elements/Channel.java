package br.dev.yann.rssreader.model.rss_elements;

import java.util.ArrayList;
import java.util.List;

import br.dev.yann.rssreader.model.rss_elements.channel_elements.Cloud;
import br.dev.yann.rssreader.model.rss_elements.channel_elements.Image;
import br.dev.yann.rssreader.model.rss_elements.channel_elements.Item;
import br.dev.yann.rssreader.model.rss_elements.channel_elements.TextInput;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {

  private String title;
  private String description;
  private String link;
  private String language;
  private String copyright;
  private String managingEditor;
  private String webMaste;
  private String pubDate;
  private String lastBuildDate;
  private String category;
  private String docs;
  private String ttl;
  private String rating;
  private String skipHours;
  private String skipDays;

  @XmlElement
  private Image image;

  @XmlElement(name = "item")
  private List<Item> items = new ArrayList<>();

  @XmlElement
  private TextInput textInput;

  @XmlElement
  private Cloud cloud;

}
