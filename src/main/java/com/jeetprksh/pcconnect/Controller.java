package com.jeetprksh.pcconnect;

import com.jeetprksh.pcconnect.client.PcConnectClient;
import com.jeetprksh.pcconnect.client.pojo.Item;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

public class Controller {

  @FXML private TextField ipAddress;
  @FXML private TextField port;
  @FXML private TextField name;
  @FXML private PasswordField code;
  @FXML private Button connect;
  @FXML private ListView<Item> listView = new ListView<>();

  private PcConnectClient client;

  public void connectServer() {
    try {
      getClient().verifyUser(this.name.getText());
      initialiseList();
      renderRootDirectories();
    } catch (Exception ex) {
      ex.printStackTrace();
      showError(ex.getLocalizedMessage());
    }
  }

  public void renderRootDirectories() throws Exception {
    List<Item> items = getClient().getRootItems();
    listView.getItems().addAll(items);
  }

  public void renderParentDirectory() {
    // TODO
  }

  public void downloadItem() throws Exception {
    FileOutputStream outputStream = null;
    InputStream stream = null;
    try {
      Item item = listView.getSelectionModel().getSelectedItem();
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

  private List<Item> getItems(String rootId, String path) throws Exception {
    return getClient().getItems(rootId, path);
  }

  private PcConnectClient getClient() {
    if (Objects.isNull(this.client)) {
      this.client = PcConnectClient.clientFactory(this.ipAddress.getText(), this.port.getText(), this.code.getText());
      return this.client;
    } else {
      return this.client;
    }
  }

  private void initialiseList() {
    this.listView.setOnMouseClicked(event -> {
      if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
        Item selectedItem = listView.getSelectionModel().getSelectedItem();
        refreshList(selectedItem.getRootAlias(), selectedItem.getPath());
      }
    });
  }

  private void refreshList(String rootAlias, String path) {
    try {
      List<Item> items = getItems(rootAlias, path);
      listView.getItems().clear();
      listView.getItems().addAll(items);
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
