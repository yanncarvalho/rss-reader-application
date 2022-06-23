package br.dev.yann.rssreader.service;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.yann.rssreader.dao.RssDao;
import br.dev.yann.rssreader.entity.User;
import br.dev.yann.rssreader.util.RssConvertorSlice;
import br.dev.yann.rssreader.util.RssPagination;

@RequestScoped
public class RssService {
  @Inject
  @Named("Rss")
  private RssDao dao;

  public Set<String> findAll(long id) {
    User user = this.dao.findById(id);
    return user.getUrlsRss();
  }

  public void deleteRss(long id, Set<String> rssUrls) {
    User user = this.dao.findById(id);
    user.removeAllUrlsRss(rssUrls);
    this.dao.deleteRss(user);
  }

  public void addRss(long id, Set<String> rssUrls) {
    User user = this.dao.findById(id);
    user.addAllUrlRss(rssUrls);
    this.dao.addRss(user);
  }

  public void deleteAllRss(long id) { this.dao.deleteAllRss(id); }

  public List<String> getRssList(long id, List<String> rssUrls) {
    List<String> urls = this.dao.findAllRssById(id);
    rssUrls.retainAll(urls);
    return rssUrls;
  }

  public RssPagination getUserRssContents(long id, int page, int size, int offset) {
    RssConvertorSlice rssSlice = new RssConvertorSlice(this.dao.findAllRssById(id), page, size, offset);
    if (rssSlice.getUnconvertable().size() > 0)
      deleteRss(id, rssSlice.getUnconvertable());
    return rssSlice.toRssPagination();
  }

  public RssPagination convertRssUrls(List<String> urls, int page, int size, int offset) {
    RssConvertorSlice rssSlice = new RssConvertorSlice(urls, page, size, offset);
    return rssSlice.toRssPagination();
  }
}



