package com.milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.Date;
import java.util.List;

public class PostingTest {

  private AccountId accountId = new AccountId("");

  @Test(expected = Posting.EmptyEntries.class)
  public void postingWithoutEntries_ShouldThrowEmptyEntries() {
    createPosting(ImmutableList.of());
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfOneEntry_ShouldThrowNotBalancedEntries() {
    createPosting(ImmutableList.of(new Entry(this.accountId, Amount.ONE)));
  }

  @Test(expected = Posting.NotBalancedEntries.class)
  public void postingOfTwoUnbalancedEntries_ShouldThrowNotBalancedEntries() {
    createPosting(ImmutableList.of(new Entry(this.accountId, Amount.ONE),
        new Entry(this.accountId, Amount.ONE)));
  }

  @Test
  public void postingOfTwoBalancedEntries() {
    createPosting(ImmutableList.of(new Entry(this.accountId, Amount.ONE),
        new Entry(this.accountId, Amount.ONE.negate())));
  }

  private void createPosting(List<Entry> entries) {
    new Posting(new PostingId(""), new Date(), entries);
  }
}
