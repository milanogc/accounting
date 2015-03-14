package com.milanogc.accounting.port.adapter.console;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.CreateAccountCommand;
import com.milanogc.accounting.application.account.EntryCommand;
import com.milanogc.accounting.application.account.PostCommand;
import com.milanogc.accounting.application.account.PostingApplicationService;
import com.milanogc.accounting.port.adapter.persistence.h2.SetupDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
@ComponentScan("com.milanogc.accounting")
public class CommandLineApp implements CommandLineRunner {

  @Autowired
  private SetupDatabase setupDatabase;
  @Autowired
  private AccountApplicationService accountApplicationService;
  @Autowired
  private PostingApplicationService postingApplicationService;

  public static void main(String[] args) {
    SpringApplication.run(CommandLineApp.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    setupDatabase.setup();
    CreateAccountCommand assetCommand = new CreateAccountCommand("Asset", null, null, new Date());
    String assetId = accountApplicationService.createAccount(assetCommand);
    CreateAccountCommand liabilityCommand = new CreateAccountCommand("Liability", null, null,
      new Date());
    String liabilityId = accountApplicationService.createAccount(liabilityCommand);
    ImmutableCollection<EntryCommand> entries = ImmutableSet.of(
      new EntryCommand(assetId, new BigDecimal("100")),
      new EntryCommand(liabilityId, new BigDecimal("-100")));
    postingApplicationService.post(new PostCommand(new Date(), entries));
  }
}
