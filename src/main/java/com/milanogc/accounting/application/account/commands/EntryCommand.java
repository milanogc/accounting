package com.milanogc.accounting.application.account.commands;

import java.math.BigDecimal;

import com.google.common.base.MoreObjects;

public class EntryCommand {

  private String accountId;
  private BigDecimal amount;

  public EntryCommand(String accountId, BigDecimal amount) {
    super();
    this.accountId = accountId;
    this.amount = amount;
  }

  public String accountId() {
    return this.accountId;
  }

  public BigDecimal amount() {
    return this.amount;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(accountId())
        .addValue(amount())
        .toString();
  }
}
