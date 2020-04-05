package com.jeetprksh.pcconnect.client.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "token"
})
public class Token {

  @JsonProperty("token")
  private String token;

  @JsonProperty("token")
  public String getToken() {
    return token;
  }

  @JsonProperty("token")
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "Token{" +
            "token='" + token + '\'' +
            '}';
  }
}

