package com.bits.ss.fitmind.security.keycloak;

import com.bits.ss.fitmind.model.ValueParser;
import org.springframework.security.core.GrantedAuthority;

public enum KeycloakAuthority implements GrantedAuthority {
  USER("user");

  private static final ValueParser<KeycloakAuthority, String> PARSER =
      ValueParser.of(KeycloakAuthority.class, KeycloakAuthority::getAuthority);

  private final String role;

  KeycloakAuthority(String roleName) {
    this.role = roleName;
  }

  @Override
  public String getAuthority() {
    return role;
  }

  public static KeycloakAuthority parseFrom(String value) {
    return PARSER.parse(value);
  }
}
