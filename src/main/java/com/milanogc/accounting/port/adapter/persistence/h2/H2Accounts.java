package com.milanogc.accounting.port.adapter.persistence.h2;

import com.milanogc.accounting.domain.account.Account;
import com.milanogc.accounting.domain.account.AccountId;
import com.milanogc.accounting.domain.account.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class H2Accounts implements Accounts {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public H2Accounts(JdbcTemplate jdbcTemplate) {
    super();
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public AccountId nextIdentity() {
    return new AccountId(UUID.randomUUID().toString().toUpperCase());
  }

  @Override
  public Account load(AccountId accountId) {
    String sql = "SELECT * FROM ACCOUNT WHERE ID = ?";
    return (Account) jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), accountId.id());
  }

  @Override
  public void store(Account account) {
    jdbcTemplate
        .update("INSERT INTO ACCOUNT (ID, NAME, CREATED_ON, DESCRIPTION, PARENT_ACCOUNT_ID)"
                + "VALUES (?, ?, ?, ?, ?)",
                account.accountId().id(), account.name(), account.createdOn(),
                account.description(),
                account.parentAccountId() != null ? account.parentAccountId().id() : null);
  }

  private static class CustomerRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
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
