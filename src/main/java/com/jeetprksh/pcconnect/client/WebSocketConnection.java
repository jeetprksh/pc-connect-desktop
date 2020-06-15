package com.jeetprksh.pcconnect.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeetprksh.pcconnect.client.pojo.Message;
import com.jeetprksh.pcconnect.client.pojo.VerifiedUser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketConnection extends WebSocketListener {

  private final Logger logger = Logger.getLogger(WebSocketConnection.class.getName());

  private final ObjectMapper mapper = new ObjectMapper();
  private final VerifiedUser verifiedUser;

  public WebSocketConnection(VerifiedUser verifiedUser) {
    this.verifiedUser = verifiedUser;
  }

  @Override
  public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
    logger.info("WebSocket opened for " + verifiedUser.getName());
    try {
      Message message = new Message("ONLINE", verifiedUser.getName());
      webSocket.send(mapper.writeValueAsString(message));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    logger.info("ON CLOSED :: " + reason);
  }

  @Override
  public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    logger.info("ON CLOSING :: " + reason);
  }

  @Override
  public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
    logger.info("ON FAILURE :: " + response.toString());
  }

  @Override
  public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
    logger.info("ON MESSAGE :: " + text);
  }

  @Override
  public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
    logger.info("ON BYTE MESSAGE");
  }

}
