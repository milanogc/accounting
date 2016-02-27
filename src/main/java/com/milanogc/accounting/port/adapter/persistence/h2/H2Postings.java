package com.milanogc.accounting.port.adapter.persistence.h2;

import com.milanogc.accounting.domain.account.Entry;
import com.milanogc.accounting.domain.account.Posting;
import com.milanogc.accounting.domain.account.PostingId;
import com.milanogc.accounting.domain.account.Postings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class H2Postings implements Postings {

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public H2Postings(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    super();
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public PostingId nextIdentity() {
    return new PostingId(UUID.randomUUID().toString().toUpperCase());
  }

  @Override
  public Posting load(PostingId postingId) {
    return null;
  }

  @Override
  public void store(Posting posting) {
    insertPosting(posting);

    for (Entry e : posting.entries()) {
      insertEntry(posting.postingId(), e);
    }
  }

  private void insertPosting(Posting posting) {
    namedParameterJdbcTemplate.getJdbcOperations().update(
        "INSERT INTO POSTING (ID, OCCURRED_ON, DESCRIPTION) VALUES (?, ?, ?)",
        posting.postingId().id(), posting.occurredOn(), posting.description());
  }

  private void insertEntry(PostingId postingId, Entry entry) {
    namedParameterJdbcTemplate.getJdbcOperations().update(
        "INSERT INTO POSTING_ENTRY (POSTING_ID, ACCOUNT_ID, AMOUNT) VALUES (?, ?, ?)",
        postingId.id(), entry.account().id(), entry.amount().value());
  }
}
