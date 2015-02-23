package milanogc.accounting.application.account;

import milanogc.accounting.domain.account.AccountService;

public class CreateAccountCommandHandler {

  private AccountService accountService;

  public CreateAccountCommandHandler(AccountService accountService) {
    this.accountService = accountService;
  }

  public void handle(CreateAccountCommand command) {
    accountService.createAccount(command.name(), command.createdOn(), command.description(),
                                 command.parentAccountId());
  }
}
