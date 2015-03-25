package com.milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;

import java.util.Objects;

// value object
public class Entry {

  private AccountId accountId;
  private Amount amount;

  public Entry(AccountId accountId, Amount amount) {
    super();
    setAccountId(accountId);
    setAmount(amount);
  }

  private void setAccountId(AccountId accountId) {
    this.accountId = Objects.requireNonNull(accountId);
  }

  private void setAmount(Amount amount) {
    errorIfInvalidAmount(amount);
    this.amount = amount;
  }

  private void errorIfInvalidAmount(Amount amount) {
    if (amount.isZero()) {
      throw new ZeroAmount();
    }
  }

  public AccountId account() {
    return this.accountId;
  }

  public Amount amount() {
    return this.amount;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .addValue(this.account())
      .addValue(this.amount())
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Entry typedObject = (Entry) o;
    return Objects.equals(this.account(), typedObject.account()) &&
        Objects.equals(this.amount(), typedObject.amount());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.account(), this.amount());
  }

  public class ZeroAmount extends RuntimeException {}
}