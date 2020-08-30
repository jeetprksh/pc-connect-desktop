package com.jeetprksh.pcconnect.controller;

import com.jeetprksh.pcconnect.client.PcConnectClient;
import com.jeetprksh.pcconnect.client.WebSocketConnection;
import com.jeetprksh.pcconnect.client.pojo.Item;
import com.jeetprksh.pcconnect.client.pojo.Message;
import com.jeetprksh.pcconnect.client.pojo.OnlineUser;
import com.jeetprksh.pcconnect.client.pojo.VerifiedUser;
import com.jeetprksh.pcconnect.persistence.dao.SettingsDao;
import com.jeetprksh.pcconnect.persistence.dao.SettingsDaoFactory;

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

public class RootController implements UIObserver {

  private final Logger logger = Logger.getLogger(RootController.class.getName());

  @FXML private TextField ipAddress;
  @FXML private TextField port;
  @FXML private TextField name;
  @FXML private PasswordField code;
  @FXML private Button connect;
  @FXML private ListView<Item> items = new ListView<>();
  @FXML private ListView<OnlineUser> users = new ListView<>();

  private PcConnectClient client;
  private VerifiedUser verifiedUser;

  private final SettingsDao settingsDao = new SettingsDaoFactory().createSettingsDao();

  public void connectServer() {
    try {
      logger.info("Connecting to the server at " + this.ipAddress.getText() + ":" + this.port.getText());
      this.verifiedUser = getClient().verifyUser(this.name.getText(), this.code.getText());
      WebSocketConnection webSocket = getClient().initializeSocket();
      webSocket.setObserver(this);
      initialiseItems();
      renderRootDirectories();
      renderUsers();
    } catch (Exception ex) {
      showError(ex.getLocalizedMessage());
    }
  }

  public void renderRootDirectories() throws Exception {
    logger.info("Rendering shared root directories");
    List<Item> items = getClient().getRootItems();
    this.items.getItems().clear();
    this.items.getItems().addAll(items);
  }

  public void renderUsers() throws Exception {
    logger.info("Rendering online users");
    List<OnlineUser> onlineUsers = getClient().getOnlineUsers();
    onlineUsers.forEach(ou -> {
      if (ou.getUserId().equalsIgnoreCase(this.verifiedUser.getId())) {
        ou.setUserName(ou.getUserName() + " (You)");
      }
    });
    this.users.getItems().clear();
    this.users.getItems().addAll(onlineUsers);
  }

  public void renderParentDirectory() {
    // TODO
  }

  public void downloadItem() throws Exception {
    logger.info("Downloading the item from server");
    FileOutputStream outputStream = null;
    InputStream stream = null;
    try {
      Item item = items.getSelectionModel().getSelectedItem();
      stream = getClient().downloadItem(item.getRootAlias(), item.getPath());
      String filePath = settingsDao.findAll().get(0).getDownloadDirectory() + File.separator + item.getName();
      outputStream = new FileOutputStream(filePath);
      outputStream.write(IOUtils.toByteArray(stream));
      logger.info("Downloaded the file at path " + filePath);
    } catch (Exception ex) {
      logger.severe("Failed to download the item " + ex.getLocalizedMessage());
      ex.printStackTrace();
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
      logger.info("Opening settings screen");
      Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pc-connect-settings.fxml"));
      Stage stage = new Stage();
      stage.setTitle("Settings");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception ex) {
      logger.severe("Failed to open settings screen " + ex.getLocalizedMessage());
      ex.printStackTrace();
    }
  }

  public void sendItemToUser() {

  }

  @Override
  public void notify(Message message) {
    // TODO Perform message specific action
  }

  public void close() {
    logger.info("Closing Root controller");
    getClient().closeSocket();
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
