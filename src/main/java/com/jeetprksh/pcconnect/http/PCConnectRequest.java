package com.jeetprksh.pcconnect.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

/**
 * @author Jeet Prakash
 */
public abstract class PCConnectRequest {

  protected final ObjectMapper mapper = new ObjectMapper();
  protected final HttpClient client;
  protected final HttpClientResponseHandler<String> responseHandler;

  public PCConnectRequest() {
    this.client = HttpClients.custom().addRequestInterceptorFirst(new RequestInterceptor()).build();
    this.responseHandler = new PCConnectResponseHandler();
  }

}
