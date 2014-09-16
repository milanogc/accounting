package milanogc.accounting.domain.account;

import java.util.Date;
import java.util.Objects;

// entity
public class Account {

  private AccountId accountId;
  private String name;
  private AccountId parentAccountId;
  private String description;
  private Date createdOn = new Date();

  public Account(AccountId accountId, String name, AccountId parentAccountId, String description, Date createdOn) {
    setAccountId(accountId);
    setName(name);
    setParentAccountId(parentAccountId);
    setDescription(description);
    setCreatedOn(createdOn);
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
    return createdOn;
  }

  private void setCreatedOn(Date createdOn) {
    this.createdOn = Objects.requireNonNull(createdOn);
  }

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
        .addValue(accountId())
        .addValue(name())
        .toString();
  }
}