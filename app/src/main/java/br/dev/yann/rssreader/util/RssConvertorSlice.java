package br.dev.yann.rssreader.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.dev.yann.rssreader.model.Rss;
import lombok.Data;

@Data
public class RssConvertorSlice{

  private List<Rss> content;

  private int page;

  private int size;

  private int offset;

  private int totalElements;

  private Set<String> unconvertable;

  public RssConvertorSlice(List<String> urls, int page, int size, int offset) {
    this.content = new ArrayList<>();
    this.unconvertable = new HashSet<>();
    this.size = size;
    this.page = page;
    this.offset = offset;
    int firstSublistIndex = page * size + offset;
    int lastSublistIndex = firstSublistIndex + size;
    int lastIndex = urls.size();
    if (firstSublistIndex < 0) {
      lastSublistIndex = firstSublistIndex = 0;
    } else if (firstSublistIndex > lastIndex) {
      lastSublistIndex = firstSublistIndex = lastIndex;
    } else if (lastSublistIndex > lastIndex) {
      lastSublistIndex = lastIndex;
    }
    this.content.addAll(getRss(urls, firstSublistIndex, lastSublistIndex));
    if (urls.size() > 0)
      this.totalElements = urls.size() - this.unconvertable.size();
  }

  private List<Rss> getRss(List<String> urls, int firstSublistIndex, int lastSublistIndex) {
    return  urls.subList(firstSublistIndex, lastSublistIndex)
                      .stream()
                      .map(url -> {
                        Rss convert = RssConvertor.get(url);
                        if (convert == null)
                          this.unconvertable.add(url);
                        return convert;
                      }).filter(r -> (r != null))
                      .collect(Collectors.toList());
     }

  public RssPagination toRssPagination() {
    return new RssPagination(this.content, this.totalElements, this.page, this.size, this.offset);
  }


}
