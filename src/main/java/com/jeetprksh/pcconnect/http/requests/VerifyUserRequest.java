package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.http.ApiUrl;
import com.jeetprksh.pcconnect.http.pojo.VerifyResponse;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.message.BasicHeader;

import java.util.Base64;

/**
 * @author Jeet Prakash
 */
public class VerifyUserRequest extends PCConnectRequest {

  private final String baseUrl;
  private final String name;
  private final String code;

  public VerifyUserRequest(String baseUrl, String name, String code) {
    this.baseUrl = baseUrl;
    this.name = name;
    this.code = code;
  }

  public VerifyResponse execute() throws Exception {
    String encodedCode = Base64.getMimeEncoder().encodeToString(code.getBytes());
    String url = baseUrl + String.format(ApiUrl.VERIFY_USER.getUrl(), name, encodedCode);
    HttpGet get = new HttpGet(url);
    get.addHeader(new BasicHeader("Content-Type", "application/json"));
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    if (response.getCode() == 200) {
      return mapper.readValue(response.getEntity().getContent(), VerifyResponse.class);
    } else {
      throw new Exception("Error verifying " + name);
    }
  }

}
