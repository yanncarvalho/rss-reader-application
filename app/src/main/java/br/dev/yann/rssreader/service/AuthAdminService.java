package br.dev.yann.rssreader.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.yann.rssreader.dao.AuthAdminDao;
import br.dev.yann.rssreader.dto.UserDTO;
import br.dev.yann.rssreader.entity.User;

@RequestScoped
public class AuthAdminService {

  @Inject
  @Named("AuthAdmin")
  private AuthAdminDao dao;

  public List<User> findAllUsers() {
    return dao.findAllUsers();
  }

  public boolean updateUserAsAdmin (UserDTO.Request.Update user){
    return (dao.update(user) != null);
  }

  public boolean deleteUserAsAdmin(Long id) {
    return dao.delete(id);
  }
  public List<Long> findAllAdminsIds() {
    return dao.findAllAdminsIds();
  }

  public Long updateAndGetFirstId() {
     dao.updateFirstIdAsAdmin();
     return dao.findFirstId();
  }

  public boolean hasUsername(String username){
    return (dao.findByUsername(username) != null);
  }

  public User findUserByIdAsAdmin(Long id) {
    return dao.findById(id);
  }

  public boolean hasUsernameWithOriginalId(String username, Long id) {
    User findByUsername = dao.findByUsername(username);
    return (findByUsername != null && findByUsername.getId() != id);
  }
}
