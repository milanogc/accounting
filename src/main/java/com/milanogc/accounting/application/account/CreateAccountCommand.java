package com.milanogc.accounting.application.account;

import com.milanogc.accounting.domain.account.AccountId;

import java.util.Date;

public class CreateAccountCommand {

  private String name;
  private AccountId parentAccountId;
  private String description;
  private Date createdOn;

  public CreateAccountCommand(String name, AccountId parentAccountId, String description,
                              Date createdOn) {
    super();
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
