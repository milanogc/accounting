package milanogc.accounting.application.commands;

import milanogc.accounting.domain.account.AccountId;
import milanogc.ddd.domain.Command;

public class CreateAccountCommand implements Command {

  private String name;
  private AccountId parentAccountId;
  private String description;

  public CreateAccountCommand(String name, AccountId parentAccountId, String description) {
    this.name = name;
    this.parentAccountId = parentAccountId;
    this.description = description;
  }

  public String name() {
    return name;
  }

  public AccountId parentAccountId() {
    return parentAccountId;
  }

  public String description() {
    return description;
  }
}
