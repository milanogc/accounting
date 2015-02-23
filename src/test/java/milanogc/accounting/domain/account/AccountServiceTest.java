package milanogc.accounting.domain.account;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import milanogc.accounting.domain.account.events.AccountCreatedDomainEvent;
import milanogc.ddd.domain.DomainEventPublisher;
import milanogc.ddd.domain.DomainEventSubscriber;

public class AccountServiceTest {
  @Before
  public void reset() {
    DomainEventPublisher.instance().reset();
  }

  @Test
  public void createAccount() {
    final boolean[] accountCreatedFlag = {false};

    DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<AccountCreatedDomainEvent>() {
      @Override
      public void handleEvent(AccountCreatedDomainEvent aDomainEvent) {
        accountCreatedFlag[0] = true;
      }

      @Override
      public Class<AccountCreatedDomainEvent> subscribedToEventType() {
        return AccountCreatedDomainEvent.class;
      }
    });

    Accounts accounts = new InMemoryAccounts();
    AccountService accountService = new AccountService(accounts);
    accountService.createAccount("ASSET", new Date(), "", null);
    Assert.assertEquals(accountCreatedFlag[0], true);
  }
}
