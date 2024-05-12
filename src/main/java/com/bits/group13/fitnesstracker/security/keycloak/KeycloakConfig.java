package com.bits.group13.fitnesstracker.security.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
  static final String serverUrl = "http://localhost:8090";

  public static final String realm = "fitness-tracker";

  private final String clientId;
  private final String clientSecret;

  static final String userName = "admin";
  static final String password = "admin";

  public KeycloakConfig(OAuth2ClientProperties oAuth2ClientProperties) {
    OAuth2ClientProperties.Registration keycloakRegistrationProperties =
        oAuth2ClientProperties.getRegistration().get("keycloak");
    clientId = keycloakRegistrationProperties.getClientId();
    clientSecret = keycloakRegistrationProperties.getClientSecret();
  }

  @Bean
  public Keycloak keycloakClient() {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .grantType(OAuth2Constants.PASSWORD)
        .username(userName)
        .password(password)
        .clientId(clientId)
        .clientSecret(clientSecret)
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
