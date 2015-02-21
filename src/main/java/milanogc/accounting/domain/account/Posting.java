package milanogc.accounting.domain.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableCollection;

import java.util.Date;
import java.util.Objects;

// entity
public class Posting {

  private PostingId postingId;
  private Date occurredOn;
  private ImmutableCollection<Entry> entries;
  private String description;

  public Posting(PostingId postingId, Date occurredOn, ImmutableCollection<Entry> entries, String description) {
    setPostingId(postingId);
    setOccurredOn(occurredOn);
    setEntries(entries);
    setDescription(description);
  }

  public Posting(PostingId postingId, Date occurredOn, ImmutableCollection<Entry> entries) {
    this(postingId, occurredOn, entries, null);
  }

  private void setPostingId(PostingId postingId) {
    this.postingId = Objects.requireNonNull(postingId, "The postingId must be provided.");
  }

  private void setOccurredOn(Date occurredOn) {
    this.occurredOn = new Date(Objects.requireNonNull(occurredOn, "The occurredOn must be provided.").getTime());
  }

  private void setEntries(ImmutableCollection<Entry> entries) {
    if (entries.isEmpty()) {
      throw new EmptyEntries();
    }

    if (!isBalanced(entries)) {
      throw new NotBalancedEntries();
    }

    this.entries = entries;
  }

  private static boolean isBalanced(ImmutableCollection<Entry> entries) {
    int sum = 0;

    for (Entry e : entries) {
      sum += e.amount();
    }

    return sum == 0;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  public PostingId postingId() {
    return postingId;
  }

  public Date occurredOn() {
    return new Date(occurredOn.getTime());
  }

  public ImmutableCollection<Entry> entries() {
    return entries;
  }

  public String description() {
    return description;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(postingId())
        .addValue(occurredOn())
        .addValue(entries())
        .addValue(description())
        .toString();
  }

  public class EmptyEntries extends RuntimeException {}

  public class NotBalancedEntries extends RuntimeException {}
}
