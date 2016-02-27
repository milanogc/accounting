package com.milanogc.accounting.domain.account;

import com.google.common.eventbus.EventBus;

import com.milanogc.accounting.domain.account.events.PostingCreatedDomainEvent;

import java.util.Date;
import java.util.List;

public class PostingService {

  private Postings postings;
  private EventBus eventBus;

  public PostingService(Postings postings, EventBus eventBus) {
    super();
    this.postings = postings;
    this.eventBus = eventBus;
  }

  public PostingId post(Date occurredOn, List<Entry> entries) {
    PostingId postingId = postings.nextIdentity();
    Posting posting = new Posting(postingId, occurredOn, entries);
    this.postings.store(posting);
    this.eventBus.post(new PostingCreatedDomainEvent());
    return postingId;
  }
}
