package com.jeetprksh.pcconnect.http.requests;

import com.jeetprksh.pcconnect.http.pojo.VerifyResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifyUserRequestTest {

  private final String baseUrl = "http://localhost:8088";

  @Test
  public void userVerifyTestPositive() throws Exception {
    VerifyUserRequest request = new VerifyUserRequest(baseUrl, "userOne", "408526");
    VerifyResponse response = request.execute();
    assertEquals("userOne", response.getUser().getUserName());
  }

  @Test
  public void userVerifyTestNegative() {
    try {
      VerifyUserRequest request = new VerifyUserRequest(baseUrl, "userOne", "blah");
      request.execute();
    } catch (Exception e) {
      assertEquals("Error verifying userOne", e.getLocalizedMessage());
    }
  }

}