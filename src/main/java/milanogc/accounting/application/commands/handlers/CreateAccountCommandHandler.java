package milanogc.accounting.application.commands.handlers;

import milanogc.accounting.application.commands.CreateAccountCommand;
import milanogc.accounting.domain.account.AccountService;

public class CreateAccountCommandHandler {

  private AccountService accountService;

  public CreateAccountCommandHandler(AccountService accountService) {
    this.accountService = accountService;
  }

  public void handle(CreateAccountCommand command) {
    accountService.createAccount(command.name(), command.parentAccountId(), command.description(), command.createdOn());
  }
}
