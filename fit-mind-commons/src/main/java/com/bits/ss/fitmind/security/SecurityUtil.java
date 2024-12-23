package com.bits.ss.fitmind.security;

import java.security.Principal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityUtil {
  public static String getOwnerId(Principal principal) {
    String userId = principal.getName();
    if (userId == null) {
      if (principal instanceof JwtAuthenticationToken token) {
        userId = token.getTokenAttributes().get(SecurityConfig.USER_ID_CLAIM).toString();
      }
    }
    if (userId == null) {
      throw new IllegalArgumentException(
          "Authentication implementation error. 'userId' is not available");
    }
    return userId;
  }
}
