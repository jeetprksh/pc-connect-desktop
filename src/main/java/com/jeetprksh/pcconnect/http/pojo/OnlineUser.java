package com.jeetprksh.pcconnect.http.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "userId",
  "userName"
})
public class OnlineUser {

  @JsonProperty("userId")
  private String userId;
  @JsonProperty("userName")
  private String userName;

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

  @Override
  public String toString() {
    return userName;
  }
}
