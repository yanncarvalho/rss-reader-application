package br.dev.yann.rssreader.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.yann.rssreader.dao.AuthAnyUserDao;
import br.dev.yann.rssreader.dto.UserDTO;
import br.dev.yann.rssreader.entity.User;

@RequestScoped
public class AuthAnyUserService {

  @Inject
  @Named("AuthAnyUser")
  private AuthAnyUserDao dao;

  public boolean hasUsername(String username) {
    return (dao.findByUsername(username) != null);
  }

  public boolean hasUserById(long id) {
    return (dao.findById(id) != null);
  }

  public void save(User user) {
      dao.save(user);
  }

  public User login(UserDTO.Request.Login user) {
    User userFound = dao.findByUsername(user.getUsername());

    if (userFound != null && userFound.authenticate(user.getPassword())) {
      return userFound;
    } else {
      return null;
    }
  }

  public void delete(Long id) {
    dao.delete(id);
  }

  public UserDTO.Response.FindAnyUser findByIdResponse(Long id) {
      return dao.findByIdReponseAnyUser(id);
  }

  public User findById(Long id) {
    return dao.findById(id);
  }

  public User update(UserDTO.Request.Update user) {
    return dao.update(user);
  }

  public boolean hasUsernameWithOriginalId(String username, Long id) {
    User findByUsername = dao.findByUsername(username);
    return (findByUsername != null && findByUsername.getId() != id);
  }
}
