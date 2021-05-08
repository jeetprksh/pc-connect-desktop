package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.client.pojo.ItemResponse;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

/**
 * @author Jeet Prakash
 */
public class GetItemRequest extends PCConnectRequest {

  private final String url;

  public GetItemRequest(String url) {
    this.url = url;
  }

  public ItemResponse execute() throws Exception {
    HttpGet get = new HttpGet(url);
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    return  mapper.readValue(response.getEntity().getContent(), ItemResponse.class);
  }

}
