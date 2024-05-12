package com.bits.group13.fitnesstracker.security.keycloak;

import java.util.function.Supplier;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public class KeycloakAuthorizationManager
    implements AuthorizationManager<RequestAuthorizationContext> {
  @Override
  public AuthorizationDecision check(
      Supplier<Authentication> authentication, RequestAuthorizationContext object) {
    return new AuthorizationDecision(
        authentication.get().getAuthorities().contains(KeycloakAuthority.USER));
  }
}
