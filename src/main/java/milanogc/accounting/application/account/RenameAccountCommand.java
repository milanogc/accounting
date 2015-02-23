package milanogc.accounting.application.account;

import milanogc.accounting.domain.account.AccountId;

public class RenameAccountCommand {

  private AccountId accountId;
  private String newName;

  public RenameAccountCommand(AccountId accountId, String newName) {
    this.accountId = accountId;
    this.newName = newName;
  }

  public AccountId accountId() {
    return accountId;
  }

  public String newName() {
    return newName;
  }
}
