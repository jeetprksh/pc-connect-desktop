package com.jeetprksh.pcconnect;

import com.jeetprksh.pcconnect.client.PcConnectClient;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

  @FXML private TextField ipAddress;
  @FXML private TextField port;
  @FXML private TextField name;
  @FXML private PasswordField code;
  @FXML private Button connect;

  private PcConnectClient client;

  public void connectServer() {
    try {
      getClient().verifyUser(this.name.getText());
    } catch (Exception ex) {
      showError(ex.getLocalizedMessage());
    }
  }

  private PcConnectClient getClient() {
    if (Objects.isNull(this.client)) {
      return PcConnectClient.clientFactory(this.ipAddress.getText(), this.port.getText(), this.code.getText());
    } else {
      return client;
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(message);
    alert.showAndWait();
  }

}
