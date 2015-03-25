package com.milanogc.accounting.port.adapter.ui.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.milanogc.accounting")
public class AccountingController {

  @RequestMapping("/")
  @ResponseBody
  public String index() {
    return "index";
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountingController.class, args);
  }
}
