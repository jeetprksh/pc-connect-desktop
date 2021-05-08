package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.client.ApiUrl;
import com.jeetprksh.pcconnect.http.PCConnectRequest;

import java.io.File;

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
    String url = baseUrl + String.format(ApiUrl.UPLOAD_ITEM.getUrl(), rootAlias, path);
    /*RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
              .addFormDataPart("file",file.getName(),
                      RequestBody.create(MediaType.parse("application/octet-stream"), file))
              .build();
      Request request = getDefaultRequestBuilder()
              .url(createBaseUrl() + String.format(ApiUrl.UPLOAD_ITEM.getUrl(), rootAlias, path))
              .post(body)
              .addHeader("token", this.verifiedUser.getToken())
              .build();
      response = client.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new Exception(response.message());
      }*/
  }

}
