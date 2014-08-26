package milanogc.accounting.domain.account;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;

import java.util.Date;
import java.util.Objects;

// entity
public class Posting {

  private PostingId postingId;
  private Date occurredOn;
  private ImmutableCollection<Entry> entries;

  public Posting(PostingId postingId, Date occurredOn, ImmutableCollection<Entry> entries) {
    setPostingId(postingId);
    setOccurredOn(occurredOn);
    setEntries(entries);
  }

  private static boolean isBalanced(ImmutableCollection<Entry> entries) {
    int sum = 0;

    for (Entry e : entries) {
      sum += e.amount();
    }

    return sum == 0;
  }

  public PostingId postingId() {
    return postingId;
  }

  private void setPostingId(PostingId postingId) {
    this.postingId = Objects.requireNonNull(postingId, "The postingId must be provided.");
  }

  public Date occurredOn() {
    return occurredOn;
  }

  private void setOccurredOn(Date occurredOn) {
    this.occurredOn = Objects.requireNonNull(occurredOn, "The occurredOn must be provided.");
  }

  public ImmutableCollection<Entry> entries() {
    return entries;
  }

  private void setEntries(ImmutableCollection<Entry> entries) {
    Preconditions.checkArgument(!entries.isEmpty(), "Entries must not be empty.");
    Preconditions.checkArgument(isBalanced(entries), "Entries must be balanced.");
    this.entries = entries;
  }

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
        .addValue(occurredOn())
        .addValue(entries())
        .toString();
  }
}
