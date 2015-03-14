package com.milanogc.accounting.application.account;

import com.google.common.collect.ImmutableSet;

import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.accounting.domain.account.Amount;
import com.milanogc.accounting.domain.account.Entry;
import com.milanogc.accounting.domain.account.PostingService;
import com.milanogc.accounting.domain.account.Postings;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

//@Service
public class PostingApplicationService {

  private Postings postings;

  @Autowired
  public PostingApplicationService(Postings postings) {
    super();
    this.postings = postings;
  }

  public void post(PostCommand command) {
    List<Entry> entries = command.entries().stream()
        .map(ec -> new Entry(new AccountId(ec.accountId()), new Amount(ec.amount())))
        .collect(Collectors.toList());
    new PostingService(postings).post(command.occurredOn(), ImmutableSet.copyOf(entries));
  }
}
