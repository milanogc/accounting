package com.milanogc.accounting.readmodel.finder.postgres.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.google.common.base.MoreObjects;

public class Entry {

  private String id;
  private String account;
  private BigDecimal amount;
  private Date occurredOn;
  private String description;
  private BigDecimal sum;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getSum() {
    return sum;
  }

  public void setSum(BigDecimal sum) {
    this.sum = sum;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(this.getId())
        .addValue(this.getAccount())
        .addValue(this.getAmount())
        .addValue(this.getOccurredOn())
        .addValue(this.getDescription())
        .addValue(this.getSum())
        .toString();
  }
}
