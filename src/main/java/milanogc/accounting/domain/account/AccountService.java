package milanogc.accounting.domain.account;

import milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;
import milanogc.ddd.domain.DomainEventPublisher;

public class AccountService {

  private Accounts accounts;
  private DomainEventPublisher domainEventPublisher;

  public AccountService(Accounts accounts,
                        DomainEventPublisher domainEventPublisher) {
    this.accounts = accounts;
    this.domainEventPublisher = domainEventPublisher;
  }

  public void createAccount(String name, AccountId parentAccountId, String description) {
    AccountId accountId = accounts.createIdentity();
    Account account = new Account(accountId, name, parentAccountId, description);
    domainEventPublisher.publish(new AccountCreatedDomainEvent(account));
  }
}
