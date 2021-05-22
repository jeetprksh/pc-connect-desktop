package com.jeetprksh.pcconnect.http.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "message",
        "data"
})
public class OnlineUserResponse {

  @JsonProperty("status")
  private boolean status;
  @JsonProperty("message")
  private String message;
  @JsonProperty("data")
  private List<OnlineUser> onlineUsers = null;

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
  public List<OnlineUser> getOnlineUsers() {
    return onlineUsers;
  }

  @JsonProperty("data")
  public void setOnlineUsers(List<OnlineUser> onlineUsers) {
    this.onlineUsers = onlineUsers;
  }

}
