package com.jeetprksh.pcconnect.connect;

import com.jeetprksh.pcconnect.http.pojo.User;
import com.jeetprksh.pcconnect.http.pojo.VerifiedUser;
import com.jeetprksh.pcconnect.http.pojo.VerifyResponse;
import com.jeetprksh.pcconnect.http.requests.VerifyUserRequest;
import com.jeetprksh.pcconnect.root.RootUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class ConnectUI {

  private final Logger logger = Logger.getLogger(ConnectUI.class.getName());

  private RootUI rootUI;

  @FXML private TextField ipAddress;
  @FXML private TextField port;
  @FXML private TextField name;
  @FXML private PasswordField code;
  @FXML private Button connect;

  public void connectServer(ActionEvent event) {
    try {
      logger.info("Connecting to the server at " + this.ipAddress.getText() + ":" + this.port.getText());
      VerifyUserRequest request = new VerifyUserRequest(createBaseUrl(), name.getText(), code.getText());
      VerifyResponse response = request.execute();
      User user = response.getUser();
      VerifiedUser verifiedUser = new VerifiedUser(
              user.getUserId(), ipAddress.getText(), port.getText(), name.getText(), user.getToken());
      rootUI.postConnectSuccess(verifiedUser);
      ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    } catch (Exception ex) {
      ex.printStackTrace();
      rootUI.postConnectFail(ex.getLocalizedMessage());
    }
  }

  public void setParent(RootUI rootUI) {
    this.rootUI = rootUI;
  }

  private String createBaseUrl() {
    return "http://" + ipAddress.getText() + ":" + port.getText();
  }
}
