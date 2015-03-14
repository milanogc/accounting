package com.milanogc.accounting.port.adapter.console;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.CreateAccountCommand;
import com.milanogc.accounting.port.adapter.persistence.h2.SetupDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

@SpringBootApplication
@ComponentScan("com.milanogc.accounting")
public class CommandLineApp implements CommandLineRunner {

  @Autowired
  private SetupDatabase setupDatabase;
  @Autowired
  private AccountApplicationService accountApplicationService;

  public static void main(String[] args) {
    SpringApplication.run(CommandLineApp.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    Args args = new Args();
    new JCommander(args, strings);

    if (args.accountName != null) {
      setupDatabase.setup();
      CreateAccountCommand
          command = new CreateAccountCommand(args.accountName, null, null, new Date());
      accountApplicationService.createAccount(command);
    }
  }

  private static class Args {

    @Parameter(names = "--account", description = "Create account")
    private String accountName;
  }
}
