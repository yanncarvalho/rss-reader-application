package br.dev.yann.rssreader.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.dev.yann.rssreader.model.Rss;
import lombok.Data;

@Data
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class RssPagination {

  private List<Rss> content;
  private int page;
  private int size;
  private int offset;
  private boolean firstPage;
  private boolean lastPage;
  private int totalPages;
  private int totalElements;

  public RssPagination(List<Rss> rss, int urlLength, int page, int size, int offset) {
    this.offset = offset;
    this.page = page;
    this.size = size;
    this.content = rss;
    this.totalElements = urlLength;

    if (offset > this.totalElements) {
      this.totalPages = 1;
    } else {
      if (size == 0)
        size = 1;
      this.totalPages = (int) Math.ceil(Math.abs(this.totalElements - offset) / (double) size);
    }
    this.firstPage = (page == 0);
    this.lastPage = (page >= totalPages - 1);
  }


}
