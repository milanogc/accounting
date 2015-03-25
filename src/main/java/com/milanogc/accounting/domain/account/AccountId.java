package com.milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;

import java.util.Objects;

// value object
public class AccountId {

  private String id;

  public AccountId(String id) {
    super();
    setId(id);
  }

  public String id() {
    return this.id;
  }

  private void setId(String id) {
    this.id = Objects.requireNonNull(id, "The id must be provided.");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AccountId typedObject = (AccountId) o;
    return this.id().equals(typedObject.id());
  }

  @Override
  public int hashCode() {
    return this.id().hashCode();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(id())
        .toString();
  }
}
