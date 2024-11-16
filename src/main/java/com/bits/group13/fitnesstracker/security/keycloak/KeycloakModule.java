package com.bits.group13.fitnesstracker.security.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakModule {
  static final String serverUrl = "http://localhost:8090";

  public static final String realm = "fitness-tracker";

  static final String userName = "admin";
  static final String password = "admin";

  @Bean
  public Keycloak keycloakClient(KeycloakConfig keycloakConfig) {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .grantType(OAuth2Constants.PASSWORD)
        .username(userName)
        .password(password)
        .clientId(keycloakConfig.getClientId())
        .clientSecret(keycloakConfig.getClientSecret())
        .resteasyClient(ResteasyClientBuilder.newClient())
        .build();
  }

  @Bean
  public UsersResource usersResource(Keycloak keycloak) {
    return keycloak.realm(realm).users();
  }

  @Bean
  public KeycloakAuthorizationManager keycloakAuthorizationManager() {
    return new KeycloakAuthorizationManager();
  }
}
