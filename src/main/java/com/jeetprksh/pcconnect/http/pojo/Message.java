package com.jeetprksh.pcconnect.http.pojo;

/*
 * @author Jeet Prakash
 * */
public class Message {

  private String type;
  private String from;

  public Message(String type, String from) {
    this.type = type;
    this.from = from;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

}