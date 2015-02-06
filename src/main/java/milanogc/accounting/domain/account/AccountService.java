package milanogc.accounting.domain.account;

import milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;
import milanogc.ddd.domain.DomainEventPublisher;

import java.util.Date;

public class AccountService {

  private Accounts accounts;

  public AccountService(Accounts accounts) {
    this.accounts = accounts;
  }

  public void createAccount(String name, AccountId parentAccountId, String description, Date createdOn) {
    AccountId accountId = accounts.createIdentity();
    Account account = new Account(accountId, name, createdOn, description, parentAccountId);
    DomainEventPublisher.instance().publish(new AccountCreatedDomainEvent(account));
  }
}
