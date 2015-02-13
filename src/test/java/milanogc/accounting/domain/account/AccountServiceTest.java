package milanogc.accounting.domain.account;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;
import milanogc.ddd.domain.DomainEventPublisher;
import milanogc.ddd.domain.DomainEventSubscriber;

public class AccountServiceTest {
  @Test
  public void createAccount() {
    final boolean[] accountCreated = {false};

    DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<AccountCreatedDomainEvent>() {
      @Override
      public void handleEvent(AccountCreatedDomainEvent aDomainEvent) {
        accountCreated[0] = true;
      }

      @Override
      public Class<AccountCreatedDomainEvent> subscribedToEventType() {
        return AccountCreatedDomainEvent.class;
      }
    });

    Accounts accounts = new InMemoryAccounts();
    AccountService accountService = new AccountService(accounts);
    accountService.createAccount("ASSET", null, "", new Date());
    DomainEventPublisher.instance().reset();
    Assert.assertEquals(accountCreated[0], true);
  }
}
