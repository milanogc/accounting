package com.milanogc.accounting.infrastructure.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.PostingApplicationService;
import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.infrastructure.importer.hledger.HledgerImporter;

@ComponentScan("com.milanogc.accounting")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CommandLineApp implements CommandLineRunner {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private AccountApplicationService accountApplicationService;
  @Autowired
  private PostingApplicationService postingApplicationService;

  public static void main(String[] args) {
    SpringApplication.run(CommandLineApp.class, args);
  }

  @Override
  public void run(String... strings) {
    /*String rootId = createAccount("ROOT", null);
    String assetId = createAccount("Asset", rootId);
    String liabilityId = createAccount("Liability", rootId);
    String equityId = createAccount("Equity", rootId);
    ImmutableList<EntryCommand> entries = ImmutableList.of(
        new EntryCommand(assetId, new BigDecimal("100")),
        new EntryCommand(liabilityId, new BigDecimal("-100")));
    this.postingApplicationService.post(new PostCommand(new Date(), entries, null));*/
    
    String home = System.getProperty("user.home");
    String fileName = home + "/.hledger.journal";
    BufferedReader reader;
    
    try {
      reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    jdbcTemplate.update("DELETE FROM POSTING_ENTRY");
    jdbcTemplate.update("DELETE FROM POSTING");
    jdbcTemplate.update("DELETE FROM ACCOUNT_CLOSURE WHERE DESCENDANT_ACCOUNT_ID <> 'ROOT'");
    jdbcTemplate.update("DELETE FROM ACCOUNT WHERE ID <> 'ROOT'");
    HledgerImporter importer = new HledgerImporter(accountApplicationService, postingApplicationService, reader);
    importer.createAccounts();
  }

  private String createAccount(String name, String parentAccountId) {
    CreateAccountCommand createAccountCommand = new CreateAccountCommand(name, parentAccountId,
        null, new Date());
    return this.accountApplicationService.createAccount(createAccountCommand);
  }
}
