package com.milanogc.accounting.application.account;

import java.math.BigDecimal;

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
}
