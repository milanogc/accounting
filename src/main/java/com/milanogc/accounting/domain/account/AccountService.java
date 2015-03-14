package com.milanogc.accounting.domain.account;

import com.milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;
import com.milanogc.ddd.domain.DomainEventPublisher;

import java.util.Date;

public class AccountService {

  private Accounts accounts;

  public AccountService(Accounts accounts) {
    super();
    this.accounts = accounts;
  }

  public void createAccount(String name, Date createdOn, String description,
                            AccountId parentAccountId) {
    AccountId newAccountId = accounts.nextIdentity();
    Account newAccount = new Account(newAccountId, name, createdOn, description, parentAccountId);
    accounts.store(newAccount);
    DomainEventPublisher.instance().publish(new AccountCreatedDomainEvent(newAccount));
  }
}
