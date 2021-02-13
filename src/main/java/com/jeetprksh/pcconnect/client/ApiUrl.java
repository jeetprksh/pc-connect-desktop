package com.jeetprksh.pcconnect.client;

/**
 * @author Jeet Prakash
 */
enum ApiUrl {

  VERIFY_USER("/user/code/verify?name=%s&encoded=%s"),
  ONLINE_USERS("/user/online"),
  GET_ITEMS("/items"),
  GET_ITEMS_PATH("/items?root=%s&path=%s"),
  DOWNLOAD_ITEM("/item/download?root=%s&path=%s&download"),
  UPLOAD_ITEM("/item/upload?root=%s&path=%s&download");

  private final String url;

  ApiUrl(String url) {
    this.url = url;
  }

  String getUrl() {
    return this.url;
  }
}
