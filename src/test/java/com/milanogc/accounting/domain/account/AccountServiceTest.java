package com.milanogc.accounting.domain.account;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import com.milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class AccountServiceTest {
  @Test
  public void createAccount() {
    EventBus eventBus = new EventBus();
    final boolean[] accountCreatedFlag = {false};

    eventBus.register(new Object() {
      @Subscribe
      public void handleEvent(AccountCreatedDomainEvent aDomainEvent) {
        accountCreatedFlag[0] = true;
      }
    });

    Accounts accounts = new InMemoryAccounts();
    AccountService accountService = new AccountService(accounts, eventBus);
    accountService.createAccount("ASSET", new Date(), "", null);
    Assert.assertEquals(accountCreatedFlag[0], true);
  }
}
