package br.dev.yann.rssreader.util;

import java.io.IOException;

import br.dev.yann.rssreader.job.RequestXmlFromHttp;
import br.dev.yann.rssreader.model.Rss;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

public class RssConvertor {
  private static RequestXmlFromHttp job = new RequestXmlFromHttp();

  private static JAXBContext context;

  private static String prepareURI(String uri) {
          return uri.replaceAll("\\s+", "");
  }

  public static Rss get(String url) {
    try {

      context = JAXBContext.newInstance(new Class[] { Rss.class });

      String treated = prepareURI(url);

      Rss rss = (Rss)context.createUnmarshaller().unmarshal(job.getXml(treated));

      rss.setOriginalLink(url);

      return rss;

    } catch (JAXBException | IOException e) {
      return null;
    }
  }
}
