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
public class ItemResponse {

  @JsonProperty("status")
  private boolean status;
  @JsonProperty("message")
  private String message;
  @JsonProperty("data")
  private List<Item> items = null;

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
  public List<Item> getItems() {
    return items;
  }

  @JsonProperty("data")
  public void setItems(List<Item> items) {
    this.items = items;
  }

}
