package com.bits.ss.fitmind.external;

import com.bits.ss.fitmind.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UsersClient {
  private final RestClient usersClient;

  public UsersClient(RestClient.Builder builder, @Value("${users.service.url}") String baseUrl) {
    this.usersClient = builder.baseUrl(baseUrl).build();
  }

  public User getUser(String userId) {
    return usersClient.get().uri("/users/" + userId).retrieve().body(User.class);
  }
}
