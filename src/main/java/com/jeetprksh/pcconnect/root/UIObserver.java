package com.jeetprksh.pcconnect.root;

import com.jeetprksh.pcconnect.http.pojo.Message;

public interface UIObserver {

  void notify(Message message);

}
