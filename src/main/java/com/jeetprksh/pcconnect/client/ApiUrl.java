package com.jeetprksh.pcconnect.client;

enum ApiUrl {

  VERIFY_USER("/user/code/verify?name=%s&encoded=%s"),
  GET_ITEMS("/items"),
  GET_ITEMS_PATH("/items?root=%s&path=%s"),
  DOWNLOAD_ITEM("/item/download?root=%s&path=%s&download");

  private String url;

  ApiUrl(String url) {
    this.url = url;
  }

  String getUrl() {
    return this.url;
  }
}
