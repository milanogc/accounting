package com.milanogc.accounting.port.adapter.ui.console;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.CreateAccountCommand;
import com.milanogc.accounting.application.account.EntryCommand;
import com.milanogc.accounting.application.account.PostCommand;
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
    CreateAccountCommand assetCommand = new CreateAccountCommand("Asset", null, null, new Date());
    String assetId = this.accountApplicationService.createAccount(assetCommand);
    CreateAccountCommand liabilityCommand = new CreateAccountCommand("Liability", null, null,
        new Date());
    String liabilityId = this.accountApplicationService.createAccount(liabilityCommand);
    ImmutableCollection<EntryCommand> entries = ImmutableSet.of(
        new EntryCommand(assetId, new BigDecimal("100")),
        new EntryCommand(liabilityId, new BigDecimal("-100")));
    this.postingApplicationService.post(new PostCommand(new Date(), entries));
  }
}
