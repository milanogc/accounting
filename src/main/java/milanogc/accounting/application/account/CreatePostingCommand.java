package milanogc.accounting.application.account;

import java.util.Collection;
import java.util.Date;

import milanogc.accounting.domain.account.Entry;

public class CreatePostingCommand {

  private Date occurredOn;
  private Collection<Entry> entries;
  private String description;

  public CreatePostingCommand(Date occurredOn,
                              Collection<Entry> entries, String description) {
    this.occurredOn = occurredOn;
    this.entries = entries;
    this.description = description;
  }

  public Date occurredOn() {
    return occurredOn;
  }

  public Collection<Entry> entries() {
    return entries;
  }

  public String description() {
    return description;
  }
}
