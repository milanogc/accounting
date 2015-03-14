package com.milanogc.accounting.domain.account;

public interface Accounts {

  AccountId nextIdentity();

  Account load(AccountId accountId);

  void store(Account account);
}