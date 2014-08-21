package milanogc.accounting.domain.account;

import com.google.common.collect.ImmutableCollection;

import java.util.Date;
import java.util.Objects;

// entity
public class Posting {

  private PostingId postingId;
  private Date occurredOn;
  private ImmutableCollection<Entry> entries;

  public Posting(PostingId postingId, Date ocurredOn, ImmutableCollection<Entry> entries) {
    setPostingId(postingId);
    setOcurredOn(ocurredOn);
    setEntries(entries);
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

  private void setOcurredOn(Date ocurredOn) {
    this.occurredOn = Objects.requireNonNull(ocurredOn, "The occurredOn must be provided.");
  }

  public ImmutableCollection<Entry> entries() {
    return entries;
  }

  private void setEntries(ImmutableCollection<Entry> entries) {
    if (entries.isEmpty()) {
      // TODO
    }

    if (!isBalanced(entries)) {
      // TODO
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

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
        .addValue(occurredOn())
        .addValue(entries())
        .toString();
  }

    /*public static class Builder {
        private PostingId postingId;
        private Date occurredOn;
        private List<Entry> entries = new ArrayList<>();

        public Builder postingId(PostingId postingId) {
            this.postingId = postingId;
            return this;
        }

        public Builder occurredOn(Date occurredOn) {
            this.occurredOn = occurredOn;
            return this;
        }

        public Builder addEntries(Collection<Entry> entries) {
            this.entries.addAll(Objects.requireNonNull(entries, "The entries must be provided."));
            return this;
        }

        public Builder debit(Account account, int amount) {
            return credit(account, -amount);
        }

        public Builder credit(Account account, int amount) {
            Entry entry = new Entry(account, amount);
            entries.add(entry);
            return this;
        }

        public Posting build() {
            return new Posting(postingId, occurredOn, ImmutableList.copyOf(entries));
        }
    }*/
}
