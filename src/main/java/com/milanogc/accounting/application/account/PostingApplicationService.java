package com.milanogc.accounting.application.account;

import com.google.common.collect.ImmutableSet;

import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.accounting.domain.account.Amount;
import com.milanogc.accounting.domain.account.Entry;
import com.milanogc.accounting.domain.account.PostingId;
import com.milanogc.accounting.domain.account.PostingService;
import com.milanogc.accounting.domain.account.Postings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingApplicationService {

  private Postings postings;

  @Autowired
  public PostingApplicationService(Postings postings) {
    super();
    this.postings = postings;
  }

  public String post(PostCommand command) {
    List<Entry> entries = command.entries().stream()
      .map(ec -> new Entry(new AccountId(ec.accountId()), new Amount(ec.amount())))
      .collect(Collectors.toList());
    PostingId postingId = new PostingService(postings).post(command.occurredOn(),
      ImmutableSet.copyOf(entries));
    return postingId.id();
  }
}
