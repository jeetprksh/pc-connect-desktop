package com.jeetprksh.pcconnect.http.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "name",
  "rootAlias",
  "path",
  "directory",
  "root"
})
public class Item {

  @JsonProperty("name")
  private String name;
  @JsonProperty("rootAlias")
  private String rootAlias;
  @JsonProperty("path")
  private String path;
  @JsonProperty("directory")
  private boolean directory;
  @JsonProperty("root")
  private boolean root;

  public Item() {

  }

  public Item(String name, boolean directory, boolean root, String rootAlias, String path) {
    this.name = name;
    this.directory = directory;
    this.root = root;
    this.rootAlias = rootAlias;
    this.path = path;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("rootAlias")
  public String getRootAlias() {
    return rootAlias;
  }

  @JsonProperty("rootAlias")
  public void setRootAlias(String rootAlias) {
    this.rootAlias = rootAlias;
  }

  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  @JsonProperty("path")
  public void setPath(String path) {
    this.path = path;
  }

  @JsonProperty("directory")
  public boolean isDirectory() {
    return directory;
  }

  @JsonProperty("directory")
  public void setDirectory(boolean directory) {
    this.directory = directory;
  }

  @JsonProperty("root")
  public boolean isRoot() {
    return root;
  }

  @JsonProperty("root")
  public void setRoot(boolean root) {
    this.root = root;
  }

  @Override
  public String toString() {
    return name;
  }
}
