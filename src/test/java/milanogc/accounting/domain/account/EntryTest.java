package milanogc.accounting.domain.account;

import org.junit.Test;

public class EntryTest {

  private Account account = new Account(new AccountId(""), "", null, null);

  @Test(expected = Entry.ZeroAmount.class)
  public void entryWithAmountOfZero_ShouldThrowZeroAmount() {
    new Entry(account, 0);
  }
}
