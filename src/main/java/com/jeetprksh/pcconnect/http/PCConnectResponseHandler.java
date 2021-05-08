package com.jeetprksh.pcconnect.http;

import org.apache.commons.io.IOUtils;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jeet Prakash
 */
class PCConnectResponseHandler implements HttpClientResponseHandler<String> {

  @Override
  public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
    if (response.getCode() == 200) {
      return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    } else {
      throw new HttpException("Error on HTTP call : " + response.getReasonPhrase());
    }
  }

}
