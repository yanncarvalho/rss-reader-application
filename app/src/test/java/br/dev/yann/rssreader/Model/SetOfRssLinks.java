package br.dev.yann.rssreader.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SetOfRssLinks {
  final private String file;

  public SetOfRssLinks() {
    this.file = "./src/test/java/br/dev/yann/rssreader/resources/RssLinks";
  }

  public Set<String> getSet() {

    Set<String> xmls = new HashSet<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

      String xmlUrl;

      while ((xmlUrl = reader.readLine()) != null) {
        xmls.add(xmlUrl);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return xmls;

  }

}
