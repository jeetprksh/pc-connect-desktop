package com.jeetprksh.pcconnect.client;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketConnection extends WebSocketListener {

  private final Logger logger = Logger.getLogger(WebSocketConnection.class.getName());

  private final String ipAddress;
  private final String port;
  private final String token;

  public WebSocketConnection(String ipAddress, String port, String token) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.token = token;
  }

  @Override
  public void onOpen(@NotNull WebSocket webSocket, Response response) {
    logger.info("ON OPEN :: " + response.toString());
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
