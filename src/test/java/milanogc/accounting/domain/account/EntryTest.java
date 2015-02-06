package milanogc.accounting.domain.account;

import org.junit.Test;

import java.util.Date;

public class EntryTest {

  private Account account = new Account(new AccountId(""), "", new Date(), null, null);

  @Test(expected = Entry.ZeroAmount.class)
  public void entryWithAmountOfZero_ShouldThrowZeroAmount() {
    new Entry(account, 0);
  }
}
