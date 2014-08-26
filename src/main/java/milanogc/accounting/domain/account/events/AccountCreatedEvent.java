package milanogc.accounting.domain.account.events;

import java.util.Date;

import milanogc.accounting.domain.account.Account;
import milanogc.ddd.domain.Event;

public class AccountCreatedEvent implements Event {

  private Date occurredOn = new Date();
  private Account account;

  public AccountCreatedEvent(Account account) {
    this.account = account;
  }

  public Account account() {
    return account;
  }

  @Override
  public Date occurredOn() {
    return occurredOn;
  }
}
