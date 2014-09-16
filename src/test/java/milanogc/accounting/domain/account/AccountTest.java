package milanogc.accounting.domain.account;

import org.junit.Test;

import java.util.Date;

public class AccountTest {

  @Test
  public void createAccount() throws Exception {
    new Account(new AccountId(""), "Assets", null, "", new Date());
  }
}
