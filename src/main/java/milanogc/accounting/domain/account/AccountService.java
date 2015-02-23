package milanogc.accounting.domain.account;

import java.util.Date;

import milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;
import milanogc.ddd.domain.DomainEventPublisher;

public class AccountService {

  private Accounts accounts;

  public AccountService(Accounts accounts) {
    this.accounts = accounts;
  }

  public void createAccount(String name, Date createdOn, String description,
                            AccountId parentAccountId) {
    AccountId newAccountId = accounts.createIdentity();
    Account newAccount = new Account(newAccountId, name, createdOn, description, parentAccountId);
    DomainEventPublisher.instance().publish(new AccountCreatedDomainEvent(newAccount));
  }
}
