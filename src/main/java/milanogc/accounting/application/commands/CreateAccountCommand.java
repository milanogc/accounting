package milanogc.accounting.application.commands;

import java.util.Date;

import milanogc.accounting.domain.account.AccountId;
import milanogc.ddd.domain.Command;

public class CreateAccountCommand implements Command {

  private String name;
  private AccountId parentAccountId;
  private String description;
  private Date createdOn;

  public CreateAccountCommand(String name, AccountId parentAccountId, String description, Date createdOn) {
    this.name = name;
    this.parentAccountId = parentAccountId;
    this.description = description;
    this.createdOn = createdOn;
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

  public Date createdOn() {
    return createdOn;
  }
}
