package com.milanogc.accounting.application.account;

import com.milanogc.accounting.domain.account.Entry;

import java.util.Collection;
import java.util.Date;

public class CreatePostingCommand {

  private Date occurredOn;
  private Collection<Entry> entries;
  private String description;

  public CreatePostingCommand(Date occurredOn, Collection<Entry> entries, String description) {
    super();
    this.occurredOn = occurredOn;
    this.entries = entries;
    this.description = description;
  }

  public Date occurredOn() {
    return this.occurredOn;
  }

  public Collection<Entry> entries() {
    return this.entries;
  }

  public String description() {
    return this.description;
  }
}
