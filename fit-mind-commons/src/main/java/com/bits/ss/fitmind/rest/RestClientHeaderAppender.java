package com.bits.ss.fitmind.rest;

import java.io.IOException;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHeaderAppender
    implements RestClientCustomizer, ClientHttpRequestInterceptor {

  @Override
  public void customize(RestClient.Builder builder) {
    builder.requestInterceptor(this);
  }

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
        String tokenValue = jwtAuthenticationToken.getToken().getTokenValue();
        if (tokenValue != null) {
          request.getHeaders().setBearerAuth(tokenValue);
        }
      } else if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
        String token = oauthToken.getPrincipal().getAttribute("access_token");
        if (token != null) {
          request.getHeaders().setBearerAuth(token);
        }
      }
    }
    return execution.execute(request, body);
  }
}
