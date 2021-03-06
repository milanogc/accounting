package com.milanogc.accounting.domain.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.milanogc.ddd.domain.Entity;

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

  private static Amount sum(List<Entry> entries) {
    Amount sum = entries.stream()
        .map(Entry::amount)
        .reduce(Amount.ZERO, (a, b) -> a.plus(b));
    return sum;
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
    Amount sum = sum(entries);
    
    if (!sum.isZero()) {
      throw new NotBalancedEntries("The sum should be `" + Amount.ZERO  + "', was `" + sum + "' instead.");
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

  public class NotBalancedEntries extends RuntimeException {

    public NotBalancedEntries(String message) {
      super(message);
    }
  }

  public static class Builder {

    private PostingId postingId;
    private Date occurredOn;
    private List<Entry> entries = new ArrayList<>();
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
      return new Posting(this.postingId, this.occurredOn, this.entries,
          this.description);
    }
  }
}
