package com.milanogc.accounting.domain.account;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class EntryTest {

  private Account account = new Account.Builder(new AccountId(""), "ASSET", new Date()).build();

  @Test(expected = Entry.ZeroAmount.class)
  public void entryWithAmountOfZero_ShouldThrowZeroAmount() {
    new Entry(account.accountId(), Amount.ZERO);
  }

  @Test
  public void entryWithAmountOfOne() {
    Entry entry = new Entry(account.accountId(), Amount.ONE);
    Assert.assertEquals(entry.amount(), Amount.ONE);
  }
}
