package com.milanogc.accounting.port.adapter.ui.web;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;

import com.milanogc.accounting.readmodel.finder.h2.H2AccountFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.milanogc.accounting")
public class AccountingController {

  @Autowired
  private H2AccountFinder accountFinder;

  @RequestMapping("/")
  @ResponseBody
  public Map<String, List<Map<String, Object>>> index() {
    List<Map<String, Object>> allAccounts = accountFinder.allAccounts().stream()
        .map(AccountingController::convertKeyFromConstantCaseToCamelCase)
        .collect(Collectors.toList());

    return ImmutableMap.of("accounts", allAccounts);
  }

  private static Map<String, Object> convertKeyFromConstantCaseToCamelCase(Map<String, Object> originalMap) {
    Map<String, Object> newMap = new LinkedHashMap<>();

    for (Map.Entry<String, Object> a : originalMap.entrySet()) {
      String newKey = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, a.getKey());
      newMap.put(newKey, a.getValue());
    }

    return newMap;
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountingController.class, args);
  }
}
