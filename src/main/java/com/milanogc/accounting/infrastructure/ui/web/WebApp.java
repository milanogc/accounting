package com.milanogc.accounting.infrastructure.ui.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.milanogc.accounting")
public class WebApp {

  public static void main(String[] args) {
    SpringApplication.run(WebApp.class, args);
  }
}
