package milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;

import java.util.Objects;

// value object
public class Entry {

  private Account account;
  private int amount;

  public Entry(Account account, int amount) {
    setAccount(account);
    setAmount(amount);
  }

  private void setAccount(Account account) {
    this.account = Objects.requireNonNull(account);
  }

  private void setAmount(int amount) {
    if (amount == 0) {
      throw new ZeroAmount();
    }

    this.amount = amount;
  }

  public Account account() {
    return account;
  }

  public int amount() {
    return amount;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(account())
        .addValue(amount())
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Entry typedObject = (Entry) o;
    return Objects.equals(account(), typedObject.account()) &&
           Objects.equals(amount(), typedObject.amount());
  }

  @Override
  public int hashCode() {
    return Objects.hash(account(), amount());
  }

  public class ZeroAmount extends RuntimeException {
  }
}