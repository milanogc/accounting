package com.milanogc.accounting.readmodel.finder.h2.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "transaction")
public class Posting {

  private Date occurredOn;
  private Entries entries;
  private String description;

  public Date getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(Date occurredOn) {
    this.occurredOn = occurredOn;
  }

  public Entries getEntries() {
    return entries;
  }

  public void setEntries(Entries entries) {
    this.entries = entries;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
