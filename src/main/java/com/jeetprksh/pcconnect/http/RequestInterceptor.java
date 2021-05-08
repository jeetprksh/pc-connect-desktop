package com.jeetprksh.pcconnect.http;

import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.util.logging.Logger;

/**
 * @author Jeet Prakash
 */
public class RequestInterceptor implements HttpRequestInterceptor {

  private final Logger logger = Logger.getLogger(RequestInterceptor.class.getName());

  @Override
  public void process(HttpRequest request,
          EntityDetails entity, HttpContext context) {
    logger.info("Request Interceptor");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("token", ""); // TODO
  }

}
