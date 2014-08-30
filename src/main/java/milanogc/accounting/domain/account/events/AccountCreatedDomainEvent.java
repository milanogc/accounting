package milanogc.accounting.domain.account.events;

import java.util.Date;

import milanogc.accounting.domain.account.Account;
import milanogc.ddd.domain.DomainEvent;

public class AccountCreatedDomainEvent implements DomainEvent {

  private Date occurredOn = new Date();
  private Account account;

  public AccountCreatedDomainEvent(Account account) {
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
