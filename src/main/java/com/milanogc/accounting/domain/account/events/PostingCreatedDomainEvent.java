package com.milanogc.accounting.domain.account.events;

import com.milanogc.ddd.domain.DomainEvent;

import java.util.Date;

public class PostingCreatedDomainEvent implements DomainEvent {

  private Date occurredOn = new Date();

  public PostingCreatedDomainEvent() {
    this.occurredOn = new Date();
  }

  @Override
  public Date occurredOn() {
    return new Date(this.occurredOn.getTime());
  }
}
