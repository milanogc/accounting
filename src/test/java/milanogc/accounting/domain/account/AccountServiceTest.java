package milanogc.accounting.domain.account;

import org.junit.Test;

import java.util.Date;

public class AccountServiceTest {
  @Test
  public void createAccount() {
    Accounts accounts = new InMemoryAccounts();
    AccountService accountService = new AccountService(accounts);
    accountService.createAccount("", null, "", new Date());
  }
}
