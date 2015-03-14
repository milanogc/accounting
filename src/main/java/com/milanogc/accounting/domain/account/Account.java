package com.milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

// entity
public class Account {

  private AccountId accountId;
  private String name;
  private Date createdOn = new Date();
  private String description;
  private AccountId parentAccountId;

  public Account(AccountId accountId, String name, Date createdOn, String description,
                 AccountId parentAccountId) {
    super();
    setAccountId(accountId);
    setName(name);
    setCreatedOn(createdOn);
    setDescription(description);
    setParentAccountId(parentAccountId);
  }

  public AccountId accountId() {
    return accountId;
  }

  private void setAccountId(AccountId accountId) {
    this.accountId = Objects.requireNonNull(accountId);
  }

  public AccountId parentAccountId() {
    return parentAccountId;
  }

  private void setParentAccountId(AccountId parentAccountId) {
    this.parentAccountId = parentAccountId;
  }

  public String name() {
    return name;
  }

  private void setName(String name) {
    this.name = Objects.requireNonNull(name);
  }

  public String description() {
    return description;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  public Date createdOn() {
    return new Date(createdOn.getTime());
  }

  private void setCreatedOn(Date createdOn) {
    this.createdOn = new Date(Objects.requireNonNull(createdOn).getTime());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(accountId())
        .addValue(name())
        .toString();
  }

  public static class Builder {

    private AccountId accountId;
    private String name;
    private AccountId parentAccountId;
    private String description;
    private Date createdOn;

    public Builder(AccountId accountId, String name, Date createdOn) {
      this.accountId = accountId;
      this.name = name;
      this.createdOn = new Date(createdOn.getTime());
    }

    public Builder parentAccountId(AccountId parentAccountId) {
      this.parentAccountId = parentAccountId;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Account build() {
      return new Account(accountId, name, createdOn, description, parentAccountId);
    }
  }
}