package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.client.ApiUrl;
import com.jeetprksh.pcconnect.client.pojo.OnlineUserResponse;
import com.jeetprksh.pcconnect.client.pojo.VerifyResponse;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import java.util.Base64;
import java.util.logging.Logger;

/**
 * @author Jeet Prakash
 */
public class OnlineUsersRequest extends PCConnectRequest {

  private final String baseUrl;

  public OnlineUsersRequest(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public OnlineUserResponse execute() throws Exception {
    String url = baseUrl + ApiUrl.ONLINE_USERS.getUrl();
    HttpGet get = new HttpGet(url);
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    return mapper.readValue(response.getEntity().getContent(), OnlineUserResponse.class);
  }

}
