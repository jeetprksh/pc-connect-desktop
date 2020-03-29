package com.jeetprksh.pcconnect.client.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "status",
  "message",
  "data"
})
public class TokenResponse {

  @JsonProperty("status")
  private boolean status;
  @JsonProperty("message")
  private String message;
  @JsonProperty("data")
  private Token token;

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
  public Token getToken() {
    return token;
  }

  @JsonProperty("data")
  public void setToken(Token token) {
    this.token = token;
  }

}
