package com.milanogc.accounting.domain.account.events;

import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.ddd.domain.DomainEvent;

import java.util.Date;
import java.util.Objects;

public class AccountCreatedDomainEvent implements DomainEvent {

  private Date occurredOn;
  private AccountId accountId;

  public AccountCreatedDomainEvent(AccountId accountId) {
    super();
    this.occurredOn = new Date();
    this.accountId = Objects.requireNonNull(accountId, "The accountId must be provided.");
  }

  public AccountId accountId() {
    return this.accountId;
  }

  @Override
  public Date occurredOn() {
    return new Date(this.occurredOn.getTime());
  }
}
