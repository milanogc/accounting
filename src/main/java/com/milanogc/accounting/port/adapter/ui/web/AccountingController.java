package com.milanogc.accounting.port.adapter.ui.web;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.readmodel.finder.h2.dto.Account;
import com.milanogc.accounting.readmodel.finder.h2.H2AccountFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.milanogc.accounting")
public class AccountingController {

  @Autowired
  private H2AccountFinder accountFinder;

  @Autowired
  private AccountApplicationService accountApplicationService;

  @RequestMapping(value = "/accounts", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, List<Map<String, Object>>> allAccounts() {
    return createNamedRootAndConvertKeysToCamelCase("accounts", accountFinder.allAccounts());
  }

  @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Account> account(@PathVariable("id") String id) {
    return ImmutableMap.of("account", accountFinder.account(id));
  }

  @RequestMapping(value = "/accounts", method = RequestMethod.POST)
  public void createAccount(Account account) {
    CreateAccountCommand command = new CreateAccountCommand(account.getName(), account.getParent(),
        account.getDescription(), new Date());
    accountApplicationService.createAccount(command);
  }

  @RequestMapping(value = "/accounts/{id}/entries", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, List<Map<String, Object>>> accountEntries(@PathVariable("id") String id) {
    return ImmutableMap.of("entries", ImmutableList.of());
  }

  private static Map<String, List<Map<String, Object>>> createNamedRootAndConvertKeysToCamelCase(
      String rootName, List<Map<String, Object>> maps) {
    return ImmutableMap.of(rootName, AccountingController.convertKeysToCamelCase(maps));
  }

  private static List<Map<String, Object>> convertKeysToCamelCase(List<Map<String, Object>> maps) {
    return maps.stream()
        .map(AccountingController::convertKeysFromConstantCaseToCamelCase)
        .collect(Collectors.toList());
  }

  private static Map<String, Object> convertKeysFromConstantCaseToCamelCase(
          Map<String, Object> mapWithConstantCaseKeys) {
    Map<String, Object> mapWithCamelCaseKeys = new LinkedHashMap<>();

    for (Map.Entry<String, Object> a : mapWithConstantCaseKeys.entrySet()) {
      String newKey = AccountingController.convertFromConstantCaseToCamelCase(a.getKey());
      mapWithCamelCaseKeys.put(newKey, a.getValue());
    }

    mapWithCamelCaseKeys.put("children", ImmutableList.of());
    return mapWithCamelCaseKeys;
  }

  private static String convertFromConstantCaseToCamelCase(String constantCase) {
    return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, constantCase);
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountingController.class, args);
  }
}
