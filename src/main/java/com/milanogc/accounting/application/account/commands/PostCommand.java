package com.milanogc.accounting.application.account.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

public class PostCommand {

  private Date occurredOn;
  private ImmutableList<EntryCommand> entries;
  private String description;

  public PostCommand(Date occurredOn, List<EntryCommand> entries,
      String description) {
    super();
    this.occurredOn = occurredOn;
    this.entries = ImmutableList.copyOf(entries);
    this.description = description;
  }

  public Date occurredOn() {
    return new Date(this.occurredOn.getTime());
  }

  public List<EntryCommand> entries() {
    return this.entries;
  }

  public String description() {
    return this.description;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(occurredOn())
        .addValue(description())
        .addValue(entries())
        .toString();
  }

  public static class Builder {
    private Date occurredOn;
    private List<EntryCommand> entries = new ArrayList<>();
    private String description;

    public Builder(Date occurredOn) {
      this.occurredOn = occurredOn;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder addEntry(String accountId, BigDecimal amount) {
      this.entries.add(new EntryCommand(accountId, amount));
      return this;
    }

    public PostCommand build() {
      return new PostCommand(this.occurredOn, this.entries, this.description);
    }
  }
}
