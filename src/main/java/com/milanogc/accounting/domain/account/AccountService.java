package com.milanogc.accounting.domain.account;

import com.google.common.eventbus.EventBus;

import com.milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;

import java.util.Date;

public class AccountService {

  private Accounts accounts;
  private EventBus eventBus;

  public AccountService(Accounts accounts, EventBus eventBus) {
    super();
    this.accounts = accounts;
    this.eventBus = eventBus;
  }

  public AccountId createAccount(String name, Date createdOn, String description,
      AccountId parentAccountId) {
    AccountId newAccountId = this.accounts.nextIdentity();
    Account newAccount = new Account(newAccountId, name, createdOn, description, parentAccountId);
    this.accounts.store(newAccount);
    this.eventBus.post(new AccountCreatedDomainEvent(newAccount.accountId()));
    return newAccountId;
  }
}
