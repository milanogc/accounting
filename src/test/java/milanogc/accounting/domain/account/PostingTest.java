package milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableSet;

import org.junit.Test;

import java.util.Date;

public class PostingTest {

  private Account account = new Account.Builder(new AccountId(""), "ASSET", new Date()).build();

  @Test(expected = Posting.EmptyEntries.class)
  public void createPosting() {
    new Posting(new PostingId(""), new Date(), ImmutableSet.of());
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfOneEntry_ShouldThrowNotBalancedEntries() {
    new Posting(new PostingId(""), new Date(), ImmutableSet.of(new Entry(account, 1)));
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfTwoUnbalancedEntries_ShouldThrowNotBalancedEntries() {
    new Posting(new PostingId(""), new Date(), ImmutableSet.of(new Entry(account, 1), new Entry(account, 1)));
  }

  @Test
  public void postingOfTwoBalancedEntries() {
    new Posting(new PostingId(""), new Date(), ImmutableSet.of(new Entry(account, 1), new Entry(account, -1)));
  }
}
