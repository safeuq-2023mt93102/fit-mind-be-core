package com.bits.group13.fitnesstracker.security.keycloak;

import com.bits.group13.fitnesstracker.model.ValueParser;
import org.springframework.security.core.GrantedAuthority;

public enum KeycloakAuthority implements GrantedAuthority {
  USER("user");

  private static final String ROLE_NAME_PREFIX = "fitness-tracker-";
  private static final ValueParser<KeycloakAuthority, String> PARSER =
      ValueParser.of(KeycloakAuthority.class, KeycloakAuthority::getAuthority);

  private final String role;

  KeycloakAuthority(String roleName) {
    this.role = ROLE_NAME_PREFIX + roleName;
  }

  @Override
  public String getAuthority() {
    return role;
  }

  public static KeycloakAuthority parseFrom(String value, String client) {
    if (client != null) {
      value = client + "-" + value;
    }
    return PARSER.parse(value);
  }
}
