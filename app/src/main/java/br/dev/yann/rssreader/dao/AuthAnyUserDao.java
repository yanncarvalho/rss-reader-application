package br.dev.yann.rssreader.dao;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.dev.yann.rssreader.dto.UserDTO;
import br.dev.yann.rssreader.entity.User;

@Stateless
@Named("AuthAnyUser")
public class AuthAnyUserDao {

  @PersistenceContext(name = "rssreader")
  private EntityManager manager;

  public void save(User user) {
    manager.persist(user);
  }

  public UserDTO.Response.FindAnyUser findByIdReponseAnyUser(Long id){
    return new UserDTO.Response.FindAnyUser(this.findById(id));
  }

  public User findById(long id) {
    return manager.find(User.class, id);
  }

  public boolean delete(Long id) {
    User user = this.findById(id);
    if(user == null){
      return false;
    }
    manager.remove(user);
    return true;

  }

  public User update(UserDTO.Request.Update user) {

    User findById = this.findById(user.getId());
    if(findById == null){
        return null;
    }
    User merge = manager.merge(findById);


    if (user.hasName()) {
      merge.setName(user.getName());
    }

    if (user.hasUsername()) {
      merge.setUsername(user.getUsername());
    }

    if (user.hasPassword()) {
      merge.setPassword(user.getPassword());
    }

    manager.flush();

    return merge;
  }

  public User findByUsername (String username) {
    TypedQuery<User> query = manager.createQuery("SELECT u FROM users u WHERE u.username = :username", User.class);
    query.setParameter("username", username);
    return query.getResultStream().findFirst().orElse(null);
  }
}
