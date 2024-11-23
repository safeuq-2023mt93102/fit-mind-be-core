package com.bits.ss.fitmind.security.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakModule {
  public static final String realm = "fitness-tracker";

  static final String userName = "admin";
  static final String password = "admin";

  @Bean
  public Keycloak keycloakClient(
      KeycloakConfig keycloakConfig, @Value("${keycloak.service.url}") String keyCloakServerUrl) {
    return KeycloakBuilder.builder()
        .serverUrl(keyCloakServerUrl)
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
