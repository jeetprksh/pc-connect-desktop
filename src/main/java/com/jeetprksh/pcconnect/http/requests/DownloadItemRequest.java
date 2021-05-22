package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.http.ApiUrl;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.message.BasicHeader;

import java.io.InputStream;

/**
 * @author Jeet Prakash
 */
public class DownloadItemRequest extends PCConnectRequest {

  private final String baseUrl;
  private final String token;
  private final String rootAlias;
  private final String path;

  public DownloadItemRequest(String baseUrl, String token, String rootAlias, String path) {
    this.baseUrl = baseUrl;
    this.token = token;
    this.rootAlias = rootAlias;
    this.path = path;
  }

  public InputStream execute() throws Exception {
    String url = baseUrl + String.format(ApiUrl.DOWNLOAD_ITEM.getUrl(), rootAlias, path);
    HttpGet get = new HttpGet(url);
    get.addHeader(new BasicHeader("token", token));
    get.addHeader(new BasicHeader("Content-Type", "application/json"));
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    return response.getEntity().getContent();
  }

}
