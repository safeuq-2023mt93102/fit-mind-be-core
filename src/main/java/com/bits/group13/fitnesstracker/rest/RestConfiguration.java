package com.bits.group13.fitnesstracker.rest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RestConfiguration {
  @Bean
  public RestClient restClient() {
    return RestClient.create();
  }

  @Bean
  public JsonMapper jsonMapper() {
    return JsonMapper.builder().findAndAddModules().build();
  }

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(10000);
    filter.setIncludeHeaders(false);
    // filter.setAfterMessagePrefix("REQUEST DATA: ");
    return filter;
  }
}
