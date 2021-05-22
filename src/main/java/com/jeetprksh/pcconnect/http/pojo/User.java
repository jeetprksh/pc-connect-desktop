package com.jeetprksh.pcconnect.http.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "userName",
        "token"
})
public class User {

  @JsonProperty("userId")
  private String userId;
  @JsonProperty("userName")
  private String userName;
  @JsonProperty("token")
  private String token;

  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  @JsonProperty("userId")
  public void setUserId(String userId) {
    this.userId = userId;
  }

  @JsonProperty("userName")
  public String getUserName() {
    return userName;
  }

  @JsonProperty("userName")
  public void setUserName(String userName) {
    this.userName = userName;
  }

  @JsonProperty("token")
  public String getToken() {
    return token;
  }

  @JsonProperty("token")
  public void setToken(String token) {
    this.token = token;
  }

}