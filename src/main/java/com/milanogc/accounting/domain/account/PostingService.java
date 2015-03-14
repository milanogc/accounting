package com.milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableCollection;

import java.util.Date;

public class PostingService {

  private Postings postings;

  public PostingService(Postings postings) {
    super();
    this.postings = postings;
  }

  public PostingId post(Date occurredOn, ImmutableCollection<Entry> entries) {
    PostingId postingId = postings.nextIdentity();
    postings.store(new Posting(postingId, occurredOn, entries));
    return postingId;
  }
}
