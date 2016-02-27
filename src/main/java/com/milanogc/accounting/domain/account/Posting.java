package com.milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import com.milanogc.ddd.domain.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Posting extends Entity {

  private PostingId postingId;
  private Date occurredOn;
  private List<Entry> entries;
  private String description;

  public Posting(PostingId postingId, Date occurredOn, List<Entry> entries,
      String description) {
    super();
    setPostingId(postingId);
    setOccurredOn(occurredOn);
    setEntries(entries);
    setDescription(description);
  }

  public Posting(PostingId postingId, Date occurredOn, List<Entry> entries) {
    this(postingId, occurredOn, entries, null);
  }

  private static boolean isBalanced(List<Entry> entries) {
    Amount sum = entries.stream()
        .map(Entry::amount)
        .reduce(Amount.ZERO, (a, b) -> a.plus(b));
    return sum.isZero();
  }

  private void setPostingId(PostingId postingId) {
    this.postingId = Objects.requireNonNull(postingId, "The postingId must be provided.");
  }

  private void setOccurredOn(Date occurredOn) {
    this.occurredOn = new Date(Objects.requireNonNull(occurredOn,
        "The occurredOn must be provided.").getTime());
  }

  private void setEntries(List<Entry> entries) {
    errorIfEmptyEntries(entries);
    errorIfNotBalancedEntries(entries);
    this.entries = ImmutableList.copyOf(entries);
  }

  private void errorIfEmptyEntries(List<Entry> entries) {
    if (entries.isEmpty()) {
      throw new EmptyEntries();
    }
  }

  private void errorIfNotBalancedEntries(List<Entry> entries) {
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

  public List<Entry> entries() {
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

  public static class Builder {

    private PostingId postingId;
    private Date occurredOn;
    private Set<Entry> entries = new HashSet<>();
    private String description;

    public Builder(PostingId postingId, Date occurredOn) {
      this.postingId = postingId;
      this.occurredOn = occurredOn;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder addEntry(AccountId accountId, Amount amount) {
      this.entries.add(new Entry(accountId, amount));
      return this;
    }

    public Posting build() {
      return new Posting(this.postingId, this.occurredOn, ImmutableList.copyOf(this.entries),
          this.description);
    }
  }
}
