package com.jeetprksh.pcconnect.websocket;

import com.jeetprksh.pcconnect.http.pojo.VerifiedUser;
import com.jeetprksh.pcconnect.root.UIObserver;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

/**
 * @author Jeet Prakash
 */
public class WebSocketConnection extends WebSocketListener {

  private final Logger logger = Logger.getLogger(WebSocketConnection.class.getName());

  private UIObserver observer;

  private final VerifiedUser verifiedUser;

  public WebSocketConnection(VerifiedUser verifiedUser) {
    this.verifiedUser = verifiedUser;
  }

  @Override
  public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
    logger.info("WebSocket opened for " + verifiedUser.getName());
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

  public void setObserver(UIObserver observer) {
    this.observer = observer;
  }

}
