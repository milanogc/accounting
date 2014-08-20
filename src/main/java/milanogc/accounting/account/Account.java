package milanogc.accounting.account;

import java.util.Date;
import java.util.Objects;

// entity
public class Account {

  private AccountId accountId;
  private String name;
  private AccountId parentAccountId;
  private String description;
  private Date createdOn = new Date();

  public Account(AccountId accountId, String name, AccountId parentAccountId, String description) {
    setAccountId(accountId);
    setName(name);
    setParentAccountId(parentAccountId);
    setDescription(description);
  }

  public AccountId accountId() {
    return accountId;
  }

  private void setAccountId(AccountId accountId) {
    this.accountId = Objects.requireNonNull(accountId, "The accountId must be provided.");
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
    this.name = Objects.requireNonNull(name, "The name must be provided.");
  }

  public String description() {
    return description;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  public Date createdOn() {
    return createdOn;
  }

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
        .addValue(accountId())
        .addValue(name())
        .toString();
  }
}