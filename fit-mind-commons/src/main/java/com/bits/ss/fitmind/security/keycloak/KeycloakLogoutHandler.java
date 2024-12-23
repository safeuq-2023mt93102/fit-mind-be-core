package com.bits.ss.fitmind.security.keycloak;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KeycloakLogoutHandler implements LogoutHandler, LogoutSuccessHandler {

  private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);
  private final RestClient restClient;

  public KeycloakLogoutHandler(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication auth) {
    logoutFromKeycloak((OidcUser) auth.getPrincipal());
  }

  private void logoutFromKeycloak(OidcUser user) {
    String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(endSessionEndpoint)
            .queryParam("id_token_hint", user.getIdToken().getTokenValue());

    ResponseEntity<String> logoutResponse =
        restClient.get().uri(builder.toUriString()).retrieve().toEntity(String.class);
    if (logoutResponse.getStatusCode().is2xxSuccessful()) {
      logger.info("Successfulley logged out from Keycloak");
    } else {
      logger.error("Could not propagate logout to Keycloak");
    }
  }

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {}
}
