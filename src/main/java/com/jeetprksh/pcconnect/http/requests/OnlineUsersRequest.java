package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.http.ApiUrl;
import com.jeetprksh.pcconnect.http.pojo.OnlineUserResponse;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.message.BasicHeader;

/**
 * @author Jeet Prakash
 */
public class OnlineUsersRequest extends PCConnectRequest {

  private final String baseUrl;
  private final String token;

  public OnlineUsersRequest(String baseUrl, String token) {
    this.baseUrl = baseUrl;
    this.token = token;
  }

  public OnlineUserResponse execute() throws Exception {
    String url = baseUrl + ApiUrl.ONLINE_USERS.getUrl();
    HttpGet get = new HttpGet(url);
    get.addHeader(new BasicHeader("token", token));
    get.addHeader(new BasicHeader("Content-Type", "application/json"));
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    return mapper.readValue(response.getEntity().getContent(), OnlineUserResponse.class);
  }

}
