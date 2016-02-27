package com.milanogc.accounting.application.account.commands;

import com.google.common.collect.ImmutableList;

import java.util.Date;
import java.util.List;

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
}
