package com.milanogc.accounting.application.account;

import com.milanogc.accounting.domain.account.AccountService;
import com.milanogc.accounting.domain.account.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountApplicationService {

  private Accounts accounts;

  @Autowired
  public AccountApplicationService(Accounts accounts) {
    super();
    this.accounts = accounts;
  }

  @Transactional
  public String createAccount(CreateAccountCommand command) {
    return new AccountService(this.accounts).createAccount(command.name(), command.createdOn(),
        command.description(), command.parentAccountId()).id();
  }
}
