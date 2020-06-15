package com.jeetprksh.pcconnect.controller;

import com.jeetprksh.pcconnect.client.PcConnectClient;
import com.jeetprksh.pcconnect.client.pojo.Item;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class RootController {

  private final Logger logger = Logger.getLogger(RootController.class.getName());

  @FXML private TextField ipAddress;
  @FXML private TextField port;
  @FXML private TextField name;
  @FXML private PasswordField code;
  @FXML private Button connect;
  @FXML private ListView<Item> items = new ListView<>();
  @FXML private ListView<Item> users = new ListView<>();

  private PcConnectClient client;

  public void connectServer() {
    try {
      getClient().verifyUser(this.name.getText(), this.code.getText());
      initialiseItems();
      renderRootDirectories();
    } catch (Exception ex) {
      ex.printStackTrace();
      showError(ex.getLocalizedMessage());
    }
  }

  public void renderRootDirectories() throws Exception {
    List<Item> items = getClient().getRootItems();
    this.items.getItems().addAll(items);
  }

  public void renderParentDirectory() {
    // TODO
  }

  public void downloadItem() throws Exception {
    FileOutputStream outputStream = null;
    InputStream stream = null;
    try {
      Item item = items.getSelectionModel().getSelectedItem();
      stream = getClient().downloadItem(item.getRootAlias(), item.getPath());
      outputStream = new FileOutputStream(new File("C:\\my-projects\\test.jpg")); // TODO pick from settings
      outputStream.write(IOUtils.toByteArray(stream));
    } finally {
      if (!Objects.isNull(outputStream)) {
        outputStream.flush();
        outputStream.close();
      }
      if (!Objects.isNull(stream)) {
        stream.close();
      }
    }
  }

  public void openSettings() {
    try {
      Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pc-connect-settings.fxml"));
      Stage stage = new Stage();
      stage.setTitle("Settings");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendItemToUser() {

  }

  private List<Item> getItems(String rootId, String path) throws Exception {
    return getClient().getItems(rootId, path);
  }

  private PcConnectClient getClient() {
    if (Objects.isNull(this.client)) {
      this.client = PcConnectClient.clientFactory(this.ipAddress.getText(), this.port.getText());
    }
    return this.client;
  }

  private void initialiseItems() {
    this.items.setOnMouseClicked(event -> {
      if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
        Item selectedItem = items.getSelectionModel().getSelectedItem();
        refreshList(selectedItem.getRootAlias(), selectedItem.getPath());
      }
    });
  }

  private void refreshList(String rootAlias, String path) {
    try {
      List<Item> items = getItems(rootAlias, path);
      this.items.getItems().clear();
      this.items.getItems().addAll(items);
    } catch (Exception e) {
      e.printStackTrace();
      showError(e.getLocalizedMessage());
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(message);
    alert.showAndWait();
  }

}
