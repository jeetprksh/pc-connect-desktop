package com.jeetprksh.pcconnect.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeetprksh.pcconnect.client.pojo.Item;
import com.jeetprksh.pcconnect.client.pojo.ItemResponse;
import com.jeetprksh.pcconnect.client.pojo.OnlineUser;
import com.jeetprksh.pcconnect.client.pojo.OnlineUserResponse;
import com.jeetprksh.pcconnect.client.pojo.User;
import com.jeetprksh.pcconnect.client.pojo.VerifyResponse;
import com.jeetprksh.pcconnect.client.pojo.VerifiedUser;

import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;

/**
 * @author Jeet Prakash
 */
public class PcConnectClient {

  private final Logger logger = Logger.getLogger(PcConnectClient.class.getName());

  private final OkHttpClient client = new OkHttpClient().newBuilder().build();
  private final ObjectMapper mapper = new ObjectMapper();

  private final String ipAddress;
  private final String port;

  private VerifiedUser verifiedUser;
  private WebSocket webSocket;

  private PcConnectClient(String ipAddress, String port) {
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public VerifiedUser verifyUser(String name, String code) throws Exception {
    logger.info("Verifying User " + name);
    Response response = null;
    try {
      String encodedCode = Base64.getMimeEncoder().encodeToString(code.getBytes());
      String url = createBaseUrl() + String.format(ApiUrl.VERIFY_USER.getUrl(), name, encodedCode);
      Request request = getDefaultRequestBuilder().url(url).get().build();
      response = client.newCall(request).execute();
      VerifyResponse verifyResponse = mapper.readValue(response.body().byteStream(), VerifyResponse.class);
      if (response.isSuccessful()) {
        logger.fine(name + " got verified");
        User user = verifyResponse.getUser();
        this.verifiedUser = new VerifiedUser(user.getUserId(), ipAddress, port, name, user.getToken());
      } else {
        logger.severe("Verification failed for " + name);
        throw new Exception(verifyResponse.getMessage());
      }
    } catch (Exception ex){
      logger.severe("Failed to verify the user"  + name);
      ex.printStackTrace();
    } finally {
      if (!Objects.isNull(response))
        response.close();
    }
    return verifiedUser;
  }

  public List<Item> getRootItems() throws Exception {
    logger.info("Fetching the list of root Items");
    return getItems(createBaseUrl() + ApiUrl.GET_ITEMS.getUrl());
  }

  public List<Item> getItems(String rootId, String path) throws Exception {
    logger.info("Fetching the list of Items for " + rootId + " " + path);
    return getItems(createBaseUrl() + String.format(ApiUrl.GET_ITEMS_PATH.getUrl(), rootId, path));
  }

  public List<OnlineUser> getOnlineUsers() throws Exception {
    logger.info("Fetching the list of online users");
    Response response = null;
    try {
      Request request = getDefaultRequestBuilder()
              .url(createBaseUrl() + ApiUrl.ONLINE_USERS.getUrl())
              .get()
              .addHeader("token", this.verifiedUser.getToken())
              .build();
      response = client.newCall(request).execute();
      OnlineUserResponse onlineUserResponse = mapper.readValue(response.body().byteStream(), OnlineUserResponse.class);
      if (response.isSuccessful()) {
        return onlineUserResponse.getOnlineUsers();
      } else {
        throw new Exception(onlineUserResponse.getMessage());
      }
    } finally {
      if (!Objects.isNull(response))
        response.close();
    }
  }

  public InputStream downloadItem(String rootAlias, String path) throws Exception {
    logger.info("Downloading the Item " + rootAlias + " " + path);
    Response response = null;
    try {
      Request request = getDefaultRequestBuilder()
              .url(createBaseUrl() + String.format(ApiUrl.DOWNLOAD_ITEM.getUrl(), rootAlias, path))
              .get()
              .addHeader("token", this.verifiedUser.getToken())
              .build();
      response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        return response.body().byteStream();
      } else {
        throw new Exception(response.message());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (!Objects.isNull(response)) {
        response.close();
      }
    }
  }

  public void uploadItem(File file, String rootAlias, String path) throws Exception {
    logger.info("Uploading the item to " + rootAlias + " " + path);
    Response response = null;
    try {
      RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
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
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      if (!Objects.isNull(response)) {
        response.close();
      }
    }
  }

  public WebSocketConnection initializeSocket() {
    WebSocketConnection socketConnection =  new WebSocketConnection(this.verifiedUser);
    Request wsRequest = new Request.Builder().url(createBaseUrl() + "/websocket")
            .addHeader("token", this.verifiedUser.getToken()).build();
    webSocket = client.newWebSocket(wsRequest, socketConnection);
    return socketConnection;
  }

  public void closeSocket() {
    if (!Objects.isNull(webSocket)) {
      webSocket.close(1001, "Client logging out");
    }
  }

  public static PcConnectClient clientFactory(String ipAddress, String port) {
    return new PcConnectClient(ipAddress, port);
  }

  private List<Item> getItems(String url) throws Exception {
    Response response = null;
    try {
      Request request = getDefaultRequestBuilder()
              .url(url)
              .get()
              .addHeader("token", this.verifiedUser.getToken())
              .build();
      response = client.newCall(request).execute();
      ItemResponse itemResponse = mapper.readValue(response.body().byteStream(), ItemResponse.class);
      if (response.isSuccessful()) {
        return itemResponse.getItems();
      } else {
        throw new Exception(itemResponse.getMessage());
      }
    } finally {
      if (!Objects.isNull(response))
        response.close();
    }
  }

  private String createBaseUrl() {
    StringBuilder url = new StringBuilder();
    return url.append("http://")
            .append(ipAddress).append(":").append(port)
            .toString();
  }

  private Request.Builder getDefaultRequestBuilder() {
    return new Request.Builder()
            .addHeader("Content-Type", "application/json");
  }

}
