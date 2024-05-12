package com.bits.group13.fitnesstracker.rest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

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
}
