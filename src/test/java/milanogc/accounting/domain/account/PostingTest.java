package milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Date;

public class PostingTest {

  private Account account = new Account(new AccountId(""), "", new Date(), null, null);

  @Test
  public void createPosting() {
    new Posting(new PostingId(""), new Date(), ImmutableSet.of());
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfOneEntry_ShouldThrowNotBalancedEntries() {
    new Posting(new PostingId(""), new Date(), ImmutableSet.of(new Entry(account, 5)));
  }
}
