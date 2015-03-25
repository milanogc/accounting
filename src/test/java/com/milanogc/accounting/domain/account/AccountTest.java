package com.milanogc.accounting.domain.account;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class AccountTest {

  private Long id = 0L;
  private Date date = new Date();

  @Test
  public void createAccount() {
    new Account.Builder(new AccountId(""), "ATIVO", date).build();
  }

  @Test
  public void createAccountWithParent() {
    Account ativo = new Account.Builder(nextIdentity(), "ATIVO", this.date).build();
    Account ativoCirculante = new Account.Builder(nextIdentity(), "ATIVO CIRCULANTE", this.date)
        .parentAccountId(ativo.accountId()).build();
    Assert.assertEquals(ativo.accountId(), ativoCirculante.parentAccountId());
  }

  private AccountId nextIdentity() {
    return new AccountId((this.id++).toString());
  }
}
