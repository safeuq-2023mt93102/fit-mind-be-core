package com.bits.group13.fitnesstracker.security.keycloak;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Component;

@Component
public class KeycloakConfig {
  private final String clientId;
  private final String clientSecret;

  public KeycloakConfig(OAuth2ClientProperties oAuth2ClientProperties) {
    OAuth2ClientProperties.Registration keycloakRegistrationProperties =
        oAuth2ClientProperties.getRegistration().get("keycloak");
    clientId = keycloakRegistrationProperties.getClientId();
    clientSecret = keycloakRegistrationProperties.getClientSecret();
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }
}
