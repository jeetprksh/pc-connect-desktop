package com.jeetprksh.pcconnect.http.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "status",
  "message",
  "data"
})
public class VerifyResponse {

  @JsonProperty("status")
  private boolean status;
  @JsonProperty("message")
  private String message;
  @JsonProperty("data")
  private User user;

  @JsonProperty("status")
  public boolean isStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(boolean status) {
    this.status = status;
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

  @JsonProperty("data")
  public User getUser() {
    return user;
  }

  @JsonProperty("data")
  public void setUser(User user) {
    this.user = user;
  }

}
