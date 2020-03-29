package com.jeetprksh.pcconnect.client;

enum ApiUrl {

  VERIFY_USER("/user/code/verify?name=%s&encoded=%s");

  private String url;

  ApiUrl(String url) {
    this.url = url;
  }

  String getUrl() {
    return this.url;
  }
}
