package milanogc.accounting.domain.account;

import milanogc.accounting.domain.account.events.AccountCreatedEvent;
import milanogc.ddd.domain.DomainEventPublisher;

public class AccountService {

  private AccountRepository accountRepository;
  private DomainEventPublisher domainEventPublisher;

  public AccountService(AccountRepository accountRepository, DomainEventPublisher domainEventPublisher) {
    this.accountRepository = accountRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  public void createAccount(String name, AccountId parentAccountId, String description) {
    AccountId accountId = accountRepository.createIdentity();
    Account account = new Account(accountId, name, parentAccountId, description);
    domainEventPublisher.publish(new AccountCreatedEvent(account));
  }
}
