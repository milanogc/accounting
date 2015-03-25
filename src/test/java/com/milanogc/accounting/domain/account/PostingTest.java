package com.milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

import org.junit.Test;

import java.util.Date;

public class PostingTest {

  private AccountId accountId = new AccountId("");

  @Test(expected = Posting.EmptyEntries.class)
  public void postingWithoutEntries_ShouldThrowEmptyEntries() {
    createPosting(ImmutableSet.of());
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfOneEntry_ShouldThrowNotBalancedEntries() {
    createPosting(ImmutableSet.of(new Entry(this.accountId, Amount.ONE)));
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfTwoUnbalancedEntries_ShouldThrowNotBalancedEntries() {
    createPosting(ImmutableSet.of(new Entry(this.accountId, Amount.ONE),
        new Entry(this.accountId, Amount.ONE)));
  }

  @Test
  public void postingOfTwoBalancedEntries() {
    createPosting(ImmutableSet.of(new Entry(this.accountId, Amount.ONE),
        new Entry(this.accountId, Amount.ONE.negate())));
  }

  private void createPosting(ImmutableCollection<Entry> entries) {
    new Posting(new PostingId(""), new Date(), entries);
  }
}
