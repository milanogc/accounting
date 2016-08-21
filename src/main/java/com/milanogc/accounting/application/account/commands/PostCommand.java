package com.milanogc.accounting.application.account.commands;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  
  public static class Builder {
    private Date occurredOn;
    private Set<EntryCommand> entries = new HashSet<>();
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
      return new PostCommand(this.occurredOn, ImmutableList.copyOf(this.entries),
          this.description);
    }
  }
}
