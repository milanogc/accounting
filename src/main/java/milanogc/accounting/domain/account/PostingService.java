package milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableCollection;

import java.util.Date;

public class PostingService {
  private PostingRepository postingRepository;

  public void post(Date occurredOn, ImmutableCollection<Entry> entries) {
    new Posting(postingRepository.nextIdentity(), occurredOn, entries);
  }
}
