package com.milanogc.accounting.application.account;

import com.milanogc.accounting.application.account.commands.EntryCommand;
import com.milanogc.accounting.application.account.commands.PostCommand;
import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.accounting.domain.account.Amount;
import com.milanogc.accounting.domain.account.Posting;
import com.milanogc.accounting.domain.account.PostingId;
import com.milanogc.accounting.domain.account.Postings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostingApplicationService {

  private Postings postings;

  @Autowired
  public PostingApplicationService(Postings postings) {
    super();
    this.postings = postings;
  }

  public String post(PostCommand command) {
    PostingId postingId = postings.nextIdentity();
    Posting.Builder postingBuilder = new Posting.Builder(postingId, command.occurredOn())
        .description(command.description());

    for (EntryCommand ec : command.entries()) {
      AccountId accountId = new AccountId(ec.accountId());
      Amount amount = new Amount(ec.amount());
      postingBuilder.addEntry(accountId, amount);
    }

    Posting posting = postingBuilder.build();
    postings.store(posting);
    return postingId.id();
  }
}
