package com.milanogc.accounting.domain.account.events;

import com.milanogc.accounting.domain.account.Account;
import com.milanogc.ddd.domain.DomainEvent;

import java.util.Date;
import java.util.Objects;

public class AccountCreatedDomainEvent implements DomainEvent {

  private Date occurredOn = new Date();
  private Account account;

  public AccountCreatedDomainEvent(Account account) {
    super();
    this.account = Objects.requireNonNull(account, "The account must be provided.");
  }

  public Account account() {
    return this.account;
  }

  @Override
  public Date occurredOn() {
    return new Date(this.occurredOn.getTime());
  }
}
