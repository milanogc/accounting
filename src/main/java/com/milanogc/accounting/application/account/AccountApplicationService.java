package com.milanogc.accounting.application.account;

import com.google.common.eventbus.EventBus;

import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.accounting.domain.account.AccountService;
import com.milanogc.accounting.domain.account.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountApplicationService {

  private EventBus eventBus;
  private Accounts accounts;

  @Autowired
  public AccountApplicationService(Accounts accounts, EventBus eventBus) {
    super();
    this.accounts = accounts;
    this.eventBus = eventBus;
  }

  @Transactional
  public String createAccount(CreateAccountCommand command) {
    AccountId parentAccountId = command.parentAccountId() == null ? null :
        new AccountId(command.parentAccountId());

    return new AccountService(this.accounts, this.eventBus).createAccount(command.name(),
        command.createdOn(), command.description(), parentAccountId).id();
  }
}
