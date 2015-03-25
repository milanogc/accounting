package com.milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableCollection;

import java.util.Date;
import java.util.Objects;

// entity
public class Posting {

  private PostingId postingId;
  private Date occurredOn;
  private ImmutableCollection<Entry> entries;
  private String description;

  public Posting(PostingId postingId, Date occurredOn, ImmutableCollection<Entry> entries,
      String description) {
    super();
    setPostingId(postingId);
    setOccurredOn(occurredOn);
    setEntries(entries);
    setDescription(description);
  }

  public Posting(PostingId postingId, Date occurredOn, ImmutableCollection<Entry> entries) {
    this(postingId, occurredOn, entries, null);
  }

  private static boolean isBalanced(ImmutableCollection<Entry> entries) {
    Amount sum = entries.stream().map(Entry::amount).reduce(Amount.ZERO, (a, b) -> a.plus(b));
    return sum.isZero();
  }

  private void setPostingId(PostingId postingId) {
    this.postingId = Objects.requireNonNull(postingId, "The postingId must be provided.");
  }

  private void setOccurredOn(Date occurredOn) {
    this.occurredOn = new Date(Objects.requireNonNull(occurredOn,
        "The occurredOn must be provided.").getTime());
  }

  private void setEntries(ImmutableCollection<Entry> entries) {
    errorIfEmptyEntries(entries);
    errorIfNotBalancedEntries(entries);
    this.entries = entries;
  }

  private void errorIfEmptyEntries(ImmutableCollection<Entry> entries) {
    if (entries.isEmpty()) {
      throw new EmptyEntries();
    }
  }

  private void errorIfNotBalancedEntries(ImmutableCollection<Entry> entries) {
    if (!isBalanced(entries)) {
      throw new NotBalancedEntries();
    }
  }

  private void setDescription(String description) {
    this.description = description;
  }

  public PostingId postingId() {
    return this.postingId;
  }

  public Date occurredOn() {
    return new Date(this.occurredOn.getTime());
  }

  public ImmutableCollection<Entry> entries() {
    return this.entries;
  }

  public String description() {
    return this.description;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(this.postingId())
        .addValue(this.occurredOn())
        .addValue(this.entries())
        .addValue(this.description())
        .toString();
  }

  public class EmptyEntries extends RuntimeException {}

  public class NotBalancedEntries extends RuntimeException {}
}
