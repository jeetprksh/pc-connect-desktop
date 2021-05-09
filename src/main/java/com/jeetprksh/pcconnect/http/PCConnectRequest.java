package com.jeetprksh.pcconnect.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/**
 * @author Jeet Prakash
 */
public abstract class PCConnectRequest {

  protected final ObjectMapper mapper = new ObjectMapper();
  protected final HttpClient client;

  public PCConnectRequest() {
    this.client = HttpClients.custom().build();
  }

}
