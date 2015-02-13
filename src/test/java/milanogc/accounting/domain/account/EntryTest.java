package milanogc.accounting.domain.account;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class EntryTest {

  private Account account = new Account.Builder(new AccountId(""), "ASSET", new Date()).build();

  @Test(expected = Entry.ZeroAmount.class)
  public void entryWithAmountOfZero_ShouldThrowZeroAmount() {
    new Entry(account, 0);
  }

  @Test
  public void entryWithAmountOfOne() {
    Entry entry = new Entry(account, 1);
    Assert.assertEquals(entry.amount(), 1);
  }
}
