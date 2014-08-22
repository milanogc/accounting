package milanogc.accounting.domain.account.events;

import milanogc.accounting.domain.account.AccountId;
import milanogc.ddd.domain.Event;

public class AccountCreatedEvent implements Event {

  private AccountId accountId;

  public AccountCreatedEvent(AccountId accountId) {
    this.accountId = accountId;
  }

  public AccountId accountId() {
    return accountId;
  }
}
