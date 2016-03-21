package com.milanogc.accounting.readmodel.finder.h2.dto;

import java.math.BigDecimal;
import java.util.Date;

public class Entry {

  private String id;
  private String account;
  private BigDecimal amount;
  private Date occurredOn;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Date getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(Date occurredOn) {
    this.occurredOn = occurredOn;
  }
}
