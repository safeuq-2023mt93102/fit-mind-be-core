package com.bits.ss.fitmind.security.keycloak;

public class KeycloakAuthorityFactory {
  private final String roleNamePrefix;

  public KeycloakAuthorityFactory(String roleName) {
    this.roleNamePrefix = roleName;
  }

  public KeycloakAuthority parseFrom(String value, String client) {
    if (!client.equals(roleNamePrefix)) {
      return null;
    }
    return KeycloakAuthority.parseFrom(value);
  }
}
