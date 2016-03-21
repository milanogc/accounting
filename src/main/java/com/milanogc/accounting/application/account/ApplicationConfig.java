package com.milanogc.accounting.application.account;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public EventBus eventBus() {
    return new EventBus();
  }
}
