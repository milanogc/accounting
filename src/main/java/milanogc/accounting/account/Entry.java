package milanogc.accounting.account;

// value object
public class Entry {

  private Account account;
  private Integer amount;

  public Entry(Account account, int amount) {
    setAccount(account);
    setAmount(amount);
  }

  public Account account() {
    return account;
  }

  private void setAccount(Account account) {
    this.account = account;
  }

  public int amount() {
    return amount;
  }

  private void setAmount(int amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
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
    return com.google.common.base.Objects.equal(account(), typedObject.account()) &&
           com.google.common.base.Objects.equal(amount(), typedObject.amount());
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(account(), amount());
  }
}