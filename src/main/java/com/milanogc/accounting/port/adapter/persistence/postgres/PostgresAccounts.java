package com.milanogc.accounting.port.adapter.persistence.postgres;

import com.google.common.collect.ImmutableMap;

import com.milanogc.accounting.domain.account.Account;
import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.accounting.domain.account.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class PostgresAccounts implements Accounts {

  private static final String INSERT_ACCOUNT =
      "INSERT INTO ACCOUNT (ID, NAME, CREATED_ON, DESCRIPTION, PARENT_ACCOUNT_ID) VALUES (?, ?, ?, ?, ?)";
  private static final String INSERT_SELF_CLOSURE =
      "INSERT INTO ACCOUNT_CLOSURE (ANCESTOR_ACCOUNT_ID, DESCENDANT_ACCOUNT_ID) VALUES (:ACCOUNT, :ACCOUNT)";
  private static final String INSERT_OTHERS_CLOSURES  =
      "INSERT INTO ACCOUNT_CLOSURE (ANCESTOR_ACCOUNT_ID, DESCENDANT_ACCOUNT_ID) " +
        "SELECT ANCESTOR_ACCOUNT_ID, :DESCENDANT FROM ACCOUNT_CLOSURE WHERE DESCENDANT_ACCOUNT_ID = :ANCESTOR";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public PostgresAccounts(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    super();
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public AccountId nextIdentity() {
    return new AccountId(UUID.randomUUID().toString().toUpperCase());
  }

  @Override
  public Account load(AccountId accountId) {
    return (Account) this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(
      "SELECT * FROM ACCOUNT WHERE ID = ?", new AccountRowMapper(), accountId.id());
  }

  @Override
  public void store(Account account) {
    String parentAccountId = getRawAccountId(account.parentAccountId());
    this.namedParameterJdbcTemplate.getJdbcOperations().update(INSERT_ACCOUNT,
        account.accountId().id(), account.name(), account.createdOn(), account.description(),
        parentAccountId);
    Map<String, Object> insertSelfClosureParameters = ImmutableMap.of("ACCOUNT",
        account.accountId().id());
    this.namedParameterJdbcTemplate.update(INSERT_SELF_CLOSURE, insertSelfClosureParameters);

    if (parentAccountId != null) {
      Map<String, Object> insertOtherClosuresParameters = ImmutableMap.of(
          "DESCENDANT", account.accountId().id(), "ANCESTOR", parentAccountId);
      this.namedParameterJdbcTemplate.update(INSERT_OTHERS_CLOSURES, insertOtherClosuresParameters);
    }
  }

  private static String getRawAccountId(AccountId accountId) {
    return accountId != null ? accountId.id() : null;
  }

  private static class AccountRowMapper implements RowMapper<Account> {

    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
      AccountId accountId = new AccountId(rs.getString("ID"));
      String name = rs.getString("NAME");
      Date createdOn = rs.getDate("CREATED_ON");
      String description = rs.getString("DESCRIPTION");
      String possibleParentAccountId = rs.getString("PARENT_ACCOUNT_ID");
      Account.Builder accountBuilder = new Account.Builder(accountId, name, createdOn)
          .description(description);

      if (possibleParentAccountId != null) {
        AccountId parentAccountId = new AccountId(possibleParentAccountId);
        accountBuilder.parentAccountId(parentAccountId);
      }

      return accountBuilder.build();
    }
  }
}
