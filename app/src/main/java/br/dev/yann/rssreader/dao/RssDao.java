package br.dev.yann.rssreader.dao;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import br.dev.yann.rssreader.entity.User;

@Named("Rss")
@RequestScoped
@SuppressWarnings({"unchecked"})
public class RssDao extends AuthAnyUserDao {

  @PersistenceContext(name = "rssreader")
  private EntityManager manager;

  @Resource
  private UserTransaction userTransaction;

  public void deleteRss(User user) {

    try {
      userTransaction.begin();
      manager.merge(user);
      userTransaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void addRss(User user) {

    try {
      userTransaction.begin();
      manager.merge(user);
      userTransaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  public List<String> findAllRssById(Long id) {
    try {
      userTransaction.begin();
      Query query = manager.createNativeQuery("SELECT urls FROM users_urls WHERE users_id = :id");
      query.setParameter("id", id);

      var urls = (List<String>) query.getResultList();
      userTransaction.commit();
      return urls;

    } catch (Exception e) {
      e.printStackTrace();
      return Collections.EMPTY_LIST;
    }
  }

  public void deleteAllRss(Long id) {
    try {
      userTransaction.begin();
      manager.createNativeQuery("DELETE FROM users_urls WHERE users_id= :id").setParameter("id", id).executeUpdate();
      userTransaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
