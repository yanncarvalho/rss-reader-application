package br.dev.yann.rssreader.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.dev.yann.rssreader.auth.PasswordEncrypt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Entity(name = "users")
public class User {

	public User(String name, String password, String username) {
    this.name = name;
    this.setPassword(password);
    this.username = username;
	}

  @Id
  @Getter
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter @Setter
  private String username;

  @Lob
  private String password;

  @Getter @Setter
  private String name;

  @Getter
  @Column(name = "is_admin")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, value ="isAdmin")
  private boolean admin = false;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> urls = new HashSet<>();


  @JsonIgnore
  public Set<String> getUrlsRss() {
    return Collections.unmodifiableSet(this.urls);
  }

  public boolean removeUrlRss(String url) {
    return this.urls.remove(url);
  }

  public boolean containsUrlRss(String url) {
    return this.urls.contains(url);
  }

  public boolean containsAllUrlRss(Set<String> urls) {
    return this.urls.containsAll(urls);
  }

  public boolean addUrlRss(String url) {
     return this.urls.add(url);

  }

  public boolean addAllUrlRss(Set<String> urls){
    return this.urls.addAll(urls);
  }

  public boolean removeAllUrlsRss (Set<String> urls){
    return this.urls.removeAll(urls);
  }

  public void cleanUrlsRss() {
     this.urls.clear();
  }

  public void setPassword(String password) {
    this.password = PasswordEncrypt.hash(password);
  }

   public boolean authenticate(String password) {
    return PasswordEncrypt.authenticate(password, this.password);
   }

}
