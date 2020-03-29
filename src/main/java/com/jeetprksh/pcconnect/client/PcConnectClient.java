package com.jeetprksh.pcconnect.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeetprksh.pcconnect.client.pojo.TokenResponse;

import java.util.Base64;
import java.util.Objects;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PcConnectClient {

  private Logger logger = Logger.getLogger(PcConnectClient.class.getName());

  private final String ipAddress;
  private final String port;
  private final String code;

  private OkHttpClient client = new OkHttpClient().newBuilder().build();

  private ObjectMapper mapper = new ObjectMapper();

  private String token;

  private PcConnectClient(String ipAddress, String port, String code) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.code = code;
  }

  public void verifyUser(String name) throws Exception {
    logger.info("Verifying User " + name);
    Response response = null;
    try {
      String encodedCode = Base64.getMimeEncoder().encodeToString(code.getBytes());
      String url = createBaseUrl() + String.format(ApiUrl.VERIFY_USER.getUrl(), name, encodedCode);
      Request request = getDefaultRequestBuilder().url(url).get().build();
      response = client.newCall(request).execute();
      TokenResponse tokenResponse = mapper.readValue(response.body().byteStream(), TokenResponse.class);
      if (response.isSuccessful()) {
        logger.fine(name + " got verified");
        this.token = tokenResponse.getToken().getToken();
      } else {
        logger.severe("Verification failed for " + name);
        throw new Exception(tokenResponse.getMessage());
      }
    } finally {
      if (!Objects.isNull(response))
        response.close();
    }
  }

  private String createBaseUrl() {
    StringBuilder url = new StringBuilder();
    return url.append("http://")
            .append(ipAddress + ":" + port)
            .toString();
  }

  private Request.Builder getDefaultRequestBuilder() {
    return new Request.Builder()
            .addHeader("Content-Type", "application/json");
  }

  public static PcConnectClient clientFactory(String ipAddress, String port, String code) {
    return new PcConnectClient(ipAddress, port, code);
  }

}
