package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.http.ApiUrl;
import com.jeetprksh.pcconnect.http.PCConnectRequest;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.message.BasicHeader;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Jeet Prakash
 */
public class UploadItemRequest extends PCConnectRequest {

  private final String baseUrl;
  private final String token;
  private final File file;
  private final String rootAlias;
  private final String path;

  public UploadItemRequest(String baseUrl, String token, File file, String rootAlias, String path) {
    this.baseUrl = baseUrl;
    this.token = token;
    this.file = file;
    this.rootAlias = rootAlias;
    this.path = path;
  }

  public void execute() throws Exception {
    String url = baseUrl + String.format(ApiUrl.UPLOAD_ITEM.getUrl(), rootAlias, URLEncoder.encode(path, StandardCharsets.UTF_8.toString()));
    HttpPost httpPost = new HttpPost(url);
    HttpEntity entity = MultipartEntityBuilder.create()
            .addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName()).build();
    httpPost.setEntity(entity);
    httpPost.addHeader(new BasicHeader("token", token));
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpPost);
    if (response.getCode() != 200) {
      throw new Exception("Error uploading the file");
    }
  }

}
