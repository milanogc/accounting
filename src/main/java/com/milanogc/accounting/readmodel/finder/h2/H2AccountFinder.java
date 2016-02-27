package com.milanogc.accounting.readmodel.finder.h2;

import com.google.common.collect.ImmutableMap;

import com.milanogc.accounting.readmodel.finder.h2.dto.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
        "ANCESTOR_ACCOUNT_ID, " +
        "DESCENDANT_ACCOUNT_ID " +
      "FROM ACCOUNT_CLOSURE " +
      "WHERE ANCESTOR_ACCOUNT_ID = ? " +
        "AND DESCENDANT_ACCOUNT_ID <> ?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public H2AccountFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Map<String, Object>> allAccounts() {
    return jdbcTemplate().queryForList(ALL_ACCOUNTS);
  }

  public Account account(String id) {
    Account account = (Account) jdbcTemplate().queryForObject(SINGLE_ACCOUNT, new String[]{id},
        new BeanPropertyRowMapper(Account.class));
    List<String> childrenAccounts = jdbcTemplate().queryForList(CHILDREN_ACCOUNTS,
        new String[]{id, id}, String.class);
    account.setChildren(childrenAccounts);
    return account;
  }

  private JdbcTemplate jdbcTemplate() {
    return this.jdbcTemplate;
  }
}
