package com.jeetprksh.pcconnect.controller;

import com.jeetprksh.pcconnect.client.pojo.Message;

public interface UIObserver {

  void notify(Message message);

}
