package com.jeetprksh.pcconnect.root;

import com.jeetprksh.pcconnect.connect.ConnectUI;
import com.jeetprksh.pcconnect.http.ApiUrl;
import com.jeetprksh.pcconnect.http.pojo.Item;
import com.jeetprksh.pcconnect.http.pojo.Message;
import com.jeetprksh.pcconnect.http.pojo.OnlineUser;
import com.jeetprksh.pcconnect.http.pojo.VerifiedUser;
import com.jeetprksh.pcconnect.http.requests.DownloadItemRequest;
import com.jeetprksh.pcconnect.http.requests.GetItemRequest;
import com.jeetprksh.pcconnect.http.requests.OnlineUsersRequest;
import com.jeetprksh.pcconnect.http.requests.UploadItemRequest;
import com.jeetprksh.pcconnect.settings.SettingDTO;
import com.jeetprksh.pcconnect.settings.SettingsDao;
import com.jeetprksh.pcconnect.settings.SettingsDaoFactory;
import com.jeetprksh.pcconnect.websocket.WebSocketConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.logging.Logger;

public class RootUI implements UIObserver {

  private final Logger logger = Logger.getLogger(RootUI.class.getName());

  @FXML private MenuBar menuBar;
  @FXML private Button sendToUser;
  @FXML private Button refreshUsers;

  @FXML private ListView<Item> items;
  @FXML private ListView<OnlineUser> users;

  private WebSocket webSocket;
  private VerifiedUser verifiedUser;

  private final Stack<Item> itemStack = new Stack<>();
  private final String ROOT_DIRECTORIES = "ROOT_DIRECTORIES";
  private final SettingsDao settingsDao = new SettingsDaoFactory().createSettingsDao();
  private final OkHttpClient client = new OkHttpClient().newBuilder().build();

  public void renderRootDirectories() throws Exception {
    logger.info("Rendering shared root directories");
    GetItemRequest request = new GetItemRequest(createBaseUrl() + ApiUrl.GET_ITEMS.getUrl(), verifiedUser.getToken());
    List<Item> items = request.execute().getItems();
    this.items.getItems().clear();
    this.items.getItems().addAll(items);
    itemStack.push(new Item(ROOT_DIRECTORIES, false, false, null, null));
  }

  public void renderParentDirectory() throws Exception {
    Item currentItem = itemStack.peek();
    if (currentItem.getName().equalsIgnoreCase(ROOT_DIRECTORIES)) {
      renderRootDirectories();
      return;
    }

    itemStack.pop();
    Item previousItem = itemStack.peek();
    if (previousItem.getName().equalsIgnoreCase(ROOT_DIRECTORIES)) {
      renderRootDirectories();
    } else {
      refreshList(previousItem.getRootAlias(), previousItem.getPath());
    }
  }

  public void downloadItem() throws Exception {
    logger.info("Downloading the item from server");
    Item item = items.getSelectionModel().getSelectedItem();
    if (Objects.isNull(item) || item.isDirectory()) {
      throw new Exception("Either no item was selected or the selected item was not a file.");
    }

    DownloadItemRequest request = new DownloadItemRequest(createBaseUrl(), verifiedUser.getToken(),
            item.getRootAlias(), URLEncoder.encode(item.getPath(), StandardCharsets.UTF_8.name()));
    try(InputStream stream = request.execute()) {
      String filePath = getDownloadDirectory() + File.separator + item.getName();
      Files.copy(stream, (new File(filePath)).toPath(), StandardCopyOption.REPLACE_EXISTING);
      logger.info("Downloaded the file at path " + filePath);
    } catch (Exception ex) {
      logger.severe("Failed to download the item " + ex.getLocalizedMessage());
      ex.printStackTrace();
      showError(ex.getLocalizedMessage());
    }
  }

  public void uploadItem() throws Exception {
    logger.info("Uploading the item to server");
    Item item = items.getSelectionModel().getSelectedItem();
    if (Objects.isNull(item) || !item.isDirectory()) {
      throw new Exception("Either no item was selected or the selected item was not a directory.");
    }

    FileChooser directoryChooser = new FileChooser();
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File selectedDirectory = directoryChooser.showOpenDialog(null);

    try {
      if (!Objects.isNull(selectedDirectory)) {
        UploadItemRequest request = new UploadItemRequest(createBaseUrl(), verifiedUser.getToken(), selectedDirectory, item.getRootAlias(), item.getPath());
        request.execute();
      }
    } catch (Exception ex) {
      logger.severe("Failed to upload the item " + ex.getLocalizedMessage());
      ex.printStackTrace();
      showError(ex.getLocalizedMessage());
    }
  }

  public void postConnectSuccess(VerifiedUser verifiedUser) throws Exception {
    this.verifiedUser = verifiedUser;
    initializeWebSocket();
    initialiseItems();
    renderRootDirectories();
    renderUsers();
  }

  public void postConnectFail(String errorMessage) {
    showError(errorMessage);
  }

  public void refreshDirectory() throws Exception {
    logger.info("Refreshing directory");
    Item currentItem = itemStack.peek();
    if (currentItem.getName().equalsIgnoreCase(ROOT_DIRECTORIES)) {
      renderRootDirectories();
    } else {
      refreshList(currentItem.getRootAlias(), currentItem.getPath());
    }
  }

  public void openConnect() {
    try {
      logger.info("Opening settings screen");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("pc-connect-connect.fxml"));
      Parent connectUI = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Connect");
      stage.setScene(new Scene(connectUI));
      stage.show();

      ConnectUI connectUi = fxmlLoader.getController();
      connectUi.setParent(this);
    } catch (Exception ex) {
      logger.severe("Failed to open settings screen " + ex.getLocalizedMessage());
      ex.printStackTrace();
    }
  }

  public void openSettings() {
    try {
      logger.info("Opening settings screen");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("pc-connect-settings.fxml"));
      Parent settingsUI = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Settings");
      stage.setScene(new Scene(settingsUI));
      stage.show();
    } catch (Exception ex) {
      logger.severe("Failed to open settings screen " + ex.getLocalizedMessage());
      ex.printStackTrace();
    }
  }

  public void renderUsers() throws Exception {
    OnlineUsersRequest request = new OnlineUsersRequest(createBaseUrl(), verifiedUser.getToken());
    List<OnlineUser> onlineUsers = request.execute().getOnlineUsers();
    onlineUsers.forEach(ou -> {
      if (ou.getUserId().equalsIgnoreCase(this.verifiedUser.getId())) {
        ou.setUserName(ou.getUserName() + " (You)");
      }
    });
    logger.info("Rendering online users " + onlineUsers);
    this.users.getItems().clear();
    this.users.getItems().addAll(onlineUsers);
  }

  public void sendItemToUser() {

  }

  public void refreshUsers() throws Exception {
    logger.info("Refreshing user list");
    renderUsers();
  }

  @Override
  public void notify(Message message) {
    // TODO Perform message specific action
  }

  public void close() {
    logger.info("Closing Root controller");
    if (!Objects.isNull(webSocket)) {
      webSocket.close(1001, "Client logging out");
    }
  }

  public void closeRoot(ActionEvent event) {
    ((Stage) menuBar.getScene().getWindow()).close();
  }

  private String createBaseUrl() {
    return "http://" + verifiedUser.getIpAddress() + ":" + verifiedUser.getPort();
  }

  private String askForDirectory() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    return directoryChooser.showDialog(null).getAbsolutePath();
  }

  private List<Item> getItems(String rootId, String path) throws Exception {
    GetItemRequest request = new GetItemRequest(createBaseUrl() + String.format(ApiUrl.GET_ITEMS_PATH.getUrl(), rootId, path), verifiedUser.getToken());
    return request.execute().getItems();
  }

  private void initializeWebSocket() {
    WebSocketConnection socketConnection =  new WebSocketConnection(this.verifiedUser);
    Request wsRequest = new Request.Builder().url(createBaseUrl() + "/websocket")
            .addHeader("token", this.verifiedUser.getToken()).build();
    webSocket = client.newWebSocket(wsRequest, socketConnection);
    socketConnection.setObserver(this);
  }

  private void initialiseItems() {
    this.items.setOnMouseClicked(event -> {
      if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
        Item selectedItem = items.getSelectionModel().getSelectedItem();
        refreshList(selectedItem.getRootAlias(), selectedItem.getPath());
        itemStack.push(selectedItem);
      }
    });
  }

  private void refreshList(String rootAlias, String path) {
    try {
      List<Item> items = getItems(rootAlias, URLEncoder.encode(path, StandardCharsets.UTF_8.name()));
      this.items.getItems().clear();
      this.items.getItems().addAll(items);
    } catch (Exception e) {
      e.printStackTrace();
      showError(e.getLocalizedMessage());
    }
  }

  private String getDownloadDirectory() {
    List<SettingDTO> allSettings = settingsDao.findAll();
    if (allSettings.isEmpty()) {
      return askForDirectory();
    } else {
      return Objects.isNull(allSettings.get(0).getSettingValue())
              ? askForDirectory() : allSettings.get(0).getSettingValue();
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(message);
    alert.showAndWait();
  }
}
