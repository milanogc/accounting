package com.milanogc.accounting.readmodel.finder.postgres.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "transaction")
public class Posting {

  private String id;
  private Date occurredOn;
  private String description;
  private Entries entries;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(Date occurredOn) {
    this.occurredOn = occurredOn;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Entries getEntries() {
    return entries;
  }

  public void setEntries(Entries entries) {
    this.entries = entries;
  }
}
