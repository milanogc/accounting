package com.milanogc.accounting.application.account;

import com.google.common.collect.ImmutableCollection;

import java.util.Date;

public class PostCommand {

  private Date occurredOn;
  private ImmutableCollection<EntryCommand> entries;

  public PostCommand(Date occurredOn, ImmutableCollection<EntryCommand> entries) {
    this.occurredOn = occurredOn;
    this.entries = entries;
  }

  public Date occurredOn() {
    return new Date(occurredOn.getTime());
  }

  public ImmutableCollection<EntryCommand> entries() {
    return entries;
  }
}
