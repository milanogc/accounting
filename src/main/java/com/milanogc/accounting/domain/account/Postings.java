package com.milanogc.accounting.domain.account;

public interface Postings {

  PostingId nextIdentity();

  Posting load(PostingId postingId);

  void store(Posting posting);
}
