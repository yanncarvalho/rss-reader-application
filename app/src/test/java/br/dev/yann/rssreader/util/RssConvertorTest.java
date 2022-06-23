package br.dev.yann.rssreader.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.AtomicDouble;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.dev.yann.rssreader.Model.SetOfRssLinks;
import jakarta.xml.bind.UnmarshalException;

public class RssConvertorTest {

  @Test
  @DisplayName("When is sent ~1k it must convert to POJO all achived rss")
  void whenIsSentAroud1kLinksItMustConvertToPOJOAllAchivedRss() throws UnmarshalException {


    SetOfRssLinks setOfRssLinks = new SetOfRssLinks();

    Set<String> urls = setOfRssLinks.getSet();
    int size = urls.size();
    double percentVariation = (double) 100 / size;
    AtomicDouble percentage = new AtomicDouble();
    AtomicInteger counter = new AtomicInteger(1);
    Set<String> unconvertable = new HashSet<>();

    urls.forEach(
        url -> {
          percentage.addAndGet(percentVariation);
          System.out.println(String.format("%.2f%% (%d/%d) - %s", percentage.get(), counter.getAndIncrement(), size, url));
           if(RssConvertor.get(url) == null){
            unconvertable.add(url);
           }
          });
          //Sometimes the link do not convert due to some problem in the server reuqested
          // side, so it is necessary analyse each case, usually, less than 10 unconvertable
          // are a suitable number
      System.out.println("-------------UNCONVERTABLE-----------------");
      unconvertable.forEach(System.out::println);
      Assertions.assertTrue(unconvertable.size()<=10);

  }
}
