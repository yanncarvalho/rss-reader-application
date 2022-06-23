package br.dev.yann.rssreader.model.rss_elements.channel_elements;

import java.util.ArrayList;
import java.util.List;

import br.dev.yann.rssreader.model.rss_elements.channel_elements.item_elements.Category;
import br.dev.yann.rssreader.model.rss_elements.channel_elements.item_elements.Enclosure;
import br.dev.yann.rssreader.model.rss_elements.channel_elements.item_elements.Guid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    private String title;
    private String link;
    private String descript;
    private String author;
    private String comments;
    private String description;


    @XmlElement
    private Enclosure enclosure = new Enclosure();


    @XmlElement
    private Guid guid;

    private String pubDate;
    private String source;

    @XmlElement(name = "category")
    private List<Category> categories = new ArrayList<>();

  }
