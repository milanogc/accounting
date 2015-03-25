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
    return this.name;
  }

  public AccountId parentAccountId() {
    return this.parentAccountId;
  }

  public String description() {
    return this.description;
  }

  public Date createdOn() {
    return this.createdOn;
  }
}
