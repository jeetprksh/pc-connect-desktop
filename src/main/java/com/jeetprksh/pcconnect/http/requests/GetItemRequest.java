package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.http.pojo.ItemResponse;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.message.BasicHeader;

/**
 * @author Jeet Prakash
 */
public class GetItemRequest extends PCConnectRequest {

  private final String url;
  private final String token;

  public GetItemRequest(String url, String token) {
    this.url = url;
    this.token = token;
  }

  public ItemResponse execute() throws Exception {
    HttpGet get = new HttpGet(url);
    get.addHeader(new BasicHeader("token", token));
    get.addHeader(new BasicHeader("Content-Type", "application/json"));
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    return  mapper.readValue(response.getEntity().getContent(), ItemResponse.class);
  }

}
