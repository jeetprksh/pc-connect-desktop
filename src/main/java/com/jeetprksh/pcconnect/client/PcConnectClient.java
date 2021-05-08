package com.jeetprksh.pcconnect.client;

import com.jeetprksh.pcconnect.client.pojo.Item;
import com.jeetprksh.pcconnect.client.pojo.ItemResponse;
import com.jeetprksh.pcconnect.client.pojo.OnlineUser;
import com.jeetprksh.pcconnect.client.pojo.OnlineUserResponse;
import com.jeetprksh.pcconnect.client.pojo.User;
import com.jeetprksh.pcconnect.client.pojo.VerifiedUser;
import com.jeetprksh.pcconnect.client.pojo.VerifyResponse;
import com.jeetprksh.pcconnect.http.requests.DownloadItemRequest;
import com.jeetprksh.pcconnect.http.requests.GetItemRequest;
import com.jeetprksh.pcconnect.http.requests.OnlineUsersRequest;
import com.jeetprksh.pcconnect.http.requests.UploadItemRequest;
import com.jeetprksh.pcconnect.http.requests.VerifyUserRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Jeet Prakash
 */
public class PcConnectClient {

  private final Logger logger = Logger.getLogger(PcConnectClient.class.getName());

  private final OkHttpClient client = new OkHttpClient().newBuilder().build();
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
    VerifyUserRequest request = new VerifyUserRequest(createBaseUrl(), name, code);
    VerifyResponse response = request.execute();
    User user = response.getUser();
    this.verifiedUser = new VerifiedUser(user.getUserId(), ipAddress, port, name, user.getToken());
    return this.verifiedUser;
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
    OnlineUsersRequest request = new OnlineUsersRequest(createBaseUrl());
    OnlineUserResponse onlineUserResponse = request.execute();
    return onlineUserResponse.getOnlineUsers();
  }

  public InputStream downloadItem(String rootAlias, String path) throws Exception {
    logger.info("Downloading the Item " + rootAlias + " " + path);
    DownloadItemRequest request = new DownloadItemRequest(createBaseUrl(), rootAlias, path);
    return request.execute();
  }

  public void uploadItem(File file, String rootAlias, String path) throws Exception {
    logger.info("Uploading the item to " + rootAlias + " " + path);
    UploadItemRequest request = new UploadItemRequest(createBaseUrl(), file, rootAlias, path);
    request.execute();
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
    GetItemRequest request = new GetItemRequest(url);
    ItemResponse itemResponse = request.execute();
    return itemResponse.getItems();
  }

  private String createBaseUrl() {
    StringBuilder url = new StringBuilder();
    return url.append("http://")
            .append(ipAddress).append(":").append(port)
            .toString();
  }

}
