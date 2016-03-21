package com.milanogc.accounting.readmodel.finder.h2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.milanogc.accounting.readmodel.finder.h2.dto.Account;
import com.milanogc.accounting.readmodel.finder.h2.dto.Accounts;

@Repository
public class H2AccountFinder {

  private static final String ALL_ACCOUNTS =
      "SELECT " +
        "ID, " +
        "NAME, " +
        "CREATED_ON, " +
        "DESCRIPTION, " +
        "PARENT_ACCOUNT_ID AS PARENT " +
      "FROM ACCOUNT";

  private static final String SINGLE_ACCOUNT =
      "SELECT " +
        "ID, " +
        "NAME, " +
        "CREATED_ON, " +
        "DESCRIPTION, " +
        "PARENT_ACCOUNT_ID AS PARENT " +
      "FROM ACCOUNT " +
      "WHERE ID = ?";

  private static final String CHILDREN_ACCOUNTS =
      "SELECT " +
        "DESCENDANT_ACCOUNT_ID " +
      "FROM ACCOUNT_CLOSURE " +
      "WHERE ANCESTOR_ACCOUNT_ID = ? " +
        "AND DESCENDANT_ACCOUNT_ID <> ?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public H2AccountFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Accounts allAccounts() {
    List<Account> accounts = jdbcTemplate.query(ALL_ACCOUNTS,
        new BeanPropertyRowMapper<Account>(Account.class));
    return new Accounts(accounts);
  }

  public Account account(String accountId) {
    Account account = jdbcTemplate.queryForObject(SINGLE_ACCOUNT, new String[]{accountId},
        new BeanPropertyRowMapper<Account>(Account.class));
    List<String> childrenAccounts = jdbcTemplate.queryForList(CHILDREN_ACCOUNTS,
        new String[]{accountId, accountId}, String.class);
    account.setChildren(childrenAccounts);
    return account;
  }
}
