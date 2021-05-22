package com.jeetprksh.pcconnect.http.pojo;

public class VerifiedUser {

  private final String id;
  private final String ipAddress;
  private final String port;
  private final String name;
  private final String token;

  public VerifiedUser(String id, String ipAddress, String port, String name, String token) {
    this.id = id;
    this.ipAddress = ipAddress;
    this.port = port;
    this.name = name;
    this.token = token;
  }

  public String getId() {
    return this.id;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public String getPort() {
    return port;
  }

  public String getName() {
    return name;
  }

  public String getToken() {
    return token;
  }
}
