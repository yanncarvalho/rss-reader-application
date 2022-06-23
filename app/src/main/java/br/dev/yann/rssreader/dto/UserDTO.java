package br.dev.yann.rssreader.dto;

import java.util.Set;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.dev.yann.rssreader.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public enum UserDTO {;

  public enum Request {;


    @Data
    public static class Login {

      @NotBlank
      private String username;

      @NotBlank
      private String password;


    };

    @Data
    public static class Save{

      @Size(min = 3, max = 255) @NotBlank
      private String password;

      @Getter
      @Size(min = 3, max = 255) @NotBlank
      private String username;

      @Getter
      @Size(min = 3, max = 255) @NotBlank
      private String name;

    };


    public static class Update {

      @Setter @Getter
      @JsonProperty(access = JsonProperty.Access.READ_ONLY)
      private long id;

      @Getter
      @Size(min = 3, max = 255) @NotBlank
      private String password = "STANDARD_VALUE";

      @JsonIgnore
      private boolean hasPassword = false;

      @Getter
      @Size(min = 3, max = 255) @NotBlank
      private String username = "STANDARD_VALUE";

      @JsonIgnore
      private boolean hasUsername = false;

      @Getter
      @Size(min = 3, max = 255) @NotBlank
      private String name = "STANDARD_VALUE";

      @JsonIgnore
      private boolean hasName = false;

      public void setPassword(String password){
        hasPassword = true;
        this.password = password;
      }

      public void setUsername(String username){
        hasUsername = true;
        this.username = password;
      }

      public void setName(String name){
        hasName = true;
        this.name = name;
      }

      public boolean hasPassword(){
        return this.hasPassword;
      }

      public boolean hasName(){
        return this.hasName;
      }

      public boolean hasUsername(){
        return this.hasUsername;
      }
    };

   };

   public enum Response {;

    @Data
    public static class FindAnyUser {
      private String username;
      private String name;
      private Set<String> urls;

     public FindAnyUser(User user){
        this.username = user.getUsername();
        this.name = user.getName();
        this.urls = user.getUrlsRss();
      }

    }

  }

}
