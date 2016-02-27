package com.milanogc.accounting.port.adapter.ui.console;

import com.google.common.collect.ImmutableList;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.application.account.commands.EntryCommand;
import com.milanogc.accounting.application.account.commands.PostCommand;
import com.milanogc.accounting.application.account.PostingApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.Date;

@ComponentScan("com.milanogc.accounting")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CommandLineApp implements CommandLineRunner {

  @Autowired
  private AccountApplicationService accountApplicationService;
  @Autowired
  private PostingApplicationService postingApplicationService;

  public static void main(String[] args) {
    SpringApplication.run(CommandLineApp.class, args);
  }

  @Override
  public void run(String... strings) {
    String rootId = createAccount("ROOT", null);
    String assetId = createAccount("Asset", rootId);
    String liabilityId = createAccount("Liability", rootId);
    String equityId = createAccount("Equity", rootId);
    ImmutableList<EntryCommand> entries = ImmutableList.of(
        new EntryCommand(assetId, new BigDecimal("100")),
        new EntryCommand(liabilityId, new BigDecimal("-100")));
    this.postingApplicationService.post(new PostCommand(new Date(), entries, null));
  }

  private String createAccount(String name, String parentAccountId) {
    CreateAccountCommand createAccountCommand = new CreateAccountCommand(name, parentAccountId,
        null, new Date());
    return this.accountApplicationService.createAccount(createAccountCommand);
  }
}
