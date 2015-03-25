package com.milanogc.accounting.port.adapter.ui.web;

import com.milanogc.accounting.readmodel.finder.h2.H2AccountFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.milanogc.accounting")
public class AccountingController {

  @Autowired
  private H2AccountFinder accountFinder;

  @RequestMapping("/")
  @ResponseBody
  public List<Map<String, Object>> index() {
    return accountFinder.allAccounts();
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountingController.class, args);
  }
}
