package milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableCollection;

import java.util.Date;

public class PostingService {

  private Postings postings;

  public void post(Date occurredOn, ImmutableCollection<Entry> entries) {
    postings.store(new Posting(postings.nextIdentity(), occurredOn, entries));
  }
}
