package com.bits.group13.fitnesstracker.security;

import com.bits.group13.fitnesstracker.security.keycloak.KeycloakAuthorityFactory;
import com.bits.group13.fitnesstracker.security.keycloak.KeycloakAuthorizationManager;
import com.bits.group13.fitnesstracker.security.keycloak.KeycloakConfig;
import com.bits.group13.fitnesstracker.security.keycloak.KeycloakLogoutHandler;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private static final String GROUPS = "groups";
  private static final String REALM_ACCESS_CLAIM = "realm_access";
  private static final String RESOURCE_ACCESS_CLAIM = "resource_access";
  private static final String ROLES_CLAIM = "roles";
  public static final String USER_ID_CLAIM = "user_id";

  private final KeycloakLogoutHandler keycloakLogoutHandler;

  public SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
    this.keycloakLogoutHandler = keycloakLogoutHandler;
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(sessionRegistry());
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter(
      Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    jwtAuthenticationConverter.setPrincipalClaimName(USER_ID_CLAIM);
    return jwtAuthenticationConverter;
  }

  @Bean
  public SecurityFilterChain configure(
      HttpSecurity httpSecurity,
      KeycloakAuthorizationManager keycloakAuthorizationManager,
      JwtAuthenticationConverter jwtAuthenticationConverter)
      throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            customizer -> customizer.requestMatchers("/auth/**", "/auth/signup").permitAll())
        .authorizeHttpRequests(
            customizer ->
                customizer
                    .requestMatchers(
                        "/goals/**", "/nutrition/**", "/plan/**", "/users/**", "/activity/**")
                    .access(keycloakAuthorizationManager))
        .oauth2ResourceServer(
            (oauth2) ->
                oauth2.jwt(
                    jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)))
        .oauth2Login(Customizer.withDefaults())
        .logout(
            logout ->
                logout
                    .logoutUrl("/auth/logout")
                    .addLogoutHandler(keycloakLogoutHandler)
                    .logoutSuccessHandler(keycloakLogoutHandler))
        .build();
  }

  //  @Bean
  //  public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
  //    return authorities -> {
  //      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
  //      var authority = authorities.iterator().next();
  //      boolean isOidc = authority instanceof OidcUserAuthority;
  //
  //      if (isOidc) {
  //        var oidcUserAuthority = (OidcUserAuthority) authority;
  //        var userInfo = oidcUserAuthority.getUserInfo();
  //
  //        // Tokens can be configured to return roles under
  //        // Groups or REALM ACCESS hence have to check both
  //        if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
  //          var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
  //          var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
  //          mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
  //        } else if (userInfo.hasClaim(GROUPS)) {
  //          Collection<String> roles = userInfo.getClaim(GROUPS);
  //          mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
  //        }
  //      } else {
  //        var oauth2UserAuthority = (OAuth2UserAuthority) authority;
  //        Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
  //
  //        if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
  //          Map<String, Object> realmAccess =
  //              (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
  //          Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
  //          mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
  //        }
  //      }
  //      return mappedAuthorities;
  //    };
  //  }

  @Bean
  public KeycloakAuthorityFactory keycloakAuthorityFactory(KeycloakConfig keycloakConfig) {
    return new KeycloakAuthorityFactory(keycloakConfig.getClientId());
  }

  @Component
  public static class KeycloakGrantedAuthorityConverter
      implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final KeycloakAuthorityFactory keycloakAuthorityFactory;

    public KeycloakGrantedAuthorityConverter(KeycloakAuthorityFactory keycloakAuthorityFactory) {
      this.keycloakAuthorityFactory = keycloakAuthorityFactory;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
      if (jwt.hasClaim(RESOURCE_ACCESS_CLAIM)) {
        var resourceAccess = jwt.getClaimAsMap(RESOURCE_ACCESS_CLAIM);
        for (var entry : resourceAccess.entrySet()) {
          String client = entry.getKey();
          var accessMap = (Map<String, Object>) entry.getValue();
          var roles = (Collection<String>) accessMap.get("roles");
          mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles, client));
        }
      } else if (jwt.hasClaim(REALM_ACCESS_CLAIM)) {
        var realmAccess = jwt.getClaimAsMap(REALM_ACCESS_CLAIM);
        var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
        mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles, null));
      } else if (jwt.hasClaim(GROUPS)) {
        Collection<String> roles = jwt.getClaim(GROUPS);
        mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles, null));
      }
      return mappedAuthorities;
    }

    private Collection<GrantedAuthority> generateAuthoritiesFromClaim(
        Collection<String> roles, String client) {
      return roles.stream()
          .map(value -> keycloakAuthorityFactory.parseFrom(value, client))
          .filter(Objects::nonNull)
          .collect(Collectors.toList());
    }
  }
}
