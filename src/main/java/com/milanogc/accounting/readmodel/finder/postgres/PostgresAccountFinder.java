package com.milanogc.accounting.readmodel.finder.postgres;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.milanogc.accounting.readmodel.finder.postgres.dto.Account;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Accounts;

@Repository
public class PostgresAccountFinder {

  private static final String ALL_ACCOUNTS =
      "SELECT " +
        "ID, " +
        "NAME, " +
        "CREATED_ON, " +
        "DESCRIPTION, " +
        "PARENT_ACCOUNT_ID AS PARENT " +
      "FROM ACCOUNT " +
      "ORDER BY NAME";

  private static final String ALL_ACCOUNTS_WITH_BALANCE_V1 =
      "SELECT " +
        "A.ID AS ID, " +
        "A.NAME AS NAME, " +
        "A.CREATED_ON AS CREATED_ON, " +
        "A.DESCRIPTION AS DESCRIPTION, " +
        "A.PARENT_ACCOUNT_ID AS PARENT, " +
        "BALANCE.AMOUNT AS BALANCE " +
      "FROM ACCOUNT A " +
        "INNER JOIN ( " +
          "SELECT " +
            "AC.ANCESTOR_ACCOUNT_ID AS ACCOUNT_ID, " +
            "SUM(PE.AMOUNT) AS AMOUNT " +
          "FROM POSTING_ENTRY PE " +
          "INNER JOIN ACCOUNT_CLOSURE AC " +
            "ON PE.ACCOUNT_ID = AC.DESCENDANT_ACCOUNT_ID " +
          "GROUP BY AC.ANCESTOR_ACCOUNT_ID " +
        ") BALANCE " +
          "ON A.ID = BALANCE.ACCOUNT_ID " +
      "ORDER BY A.NAME";

  private static final String ALL_ACCOUNTS_WITH_BALANCE_V2 =
      "SELECT " +
        "A.ID AS ID, " +
        "A.NAME AS NAME, " +
        "A.CREATED_ON AS CREATED_ON, " +
        "A.DESCRIPTION AS DESCRIPTION, " +
        "A.PARENT_ACCOUNT_ID AS PARENT, " +
        "BALANCE.AMOUNT AS BALANCE " +
      "FROM ACCOUNT A " +
      "INNER JOIN ( " +
        "WITH RECURSIVE CTE AS ( " +
          "SELECT " +
            "A.ID, " +
            "A.PARENT_ACCOUNT_ID, " +
            "SUM(PE.AMOUNT) AS AMOUNT " +
          "FROM ACCOUNT A " +
          "INNER JOIN POSTING_ENTRY PE " +
            "ON A.ID = PE.ACCOUNT_ID " +
          "GROUP BY A.ID " +
          "UNION ALL " +
          "SELECT " +
            "A.ID, " +
            "A.PARENT_ACCOUNT_ID, " +
            "CTE.AMOUNT " +
          "FROM ACCOUNT A " +
          "INNER JOIN CTE " +
            "ON A.ID = CTE.PARENT_ACCOUNT_ID" +
        ") " +
        "SELECT " +
          "ID, " +
          "SUM(AMOUNT) AS AMOUNT " +
        "FROM CTE " +
        "GROUP BY ID " +
      ") BALANCE " +
        "ON A.ID = BALANCE.ID " +
      "ORDER BY A.NAME";

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
        "ID " +
      "FROM ACCOUNT " +
      "WHERE PARENT_ACCOUNT_ID = ? " +
      "ORDER BY NAME";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PostgresAccountFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Accounts allAccounts() {
    List<Account> accounts = jdbcTemplate.query(ALL_ACCOUNTS_WITH_BALANCE_V2,
        new BeanPropertyRowMapper<Account>(Account.class));
    Map<String, List<String>> parentChildren = accounts.stream()
        .filter(a -> a.getParent() != null)
        .collect(Collectors.groupingBy(Account::getParent, Collectors.mapping(Account::getId, Collectors.toList())));
    accounts.stream().forEach(a -> a.setChildren(parentChildren.get(a.getId())));
    return new Accounts(accounts);
  }

  public Account account(String accountId) {
    Account account = jdbcTemplate.queryForObject(SINGLE_ACCOUNT, new String[]{accountId},
        new BeanPropertyRowMapper<Account>(Account.class));
    List<String> childrenAccounts = jdbcTemplate.queryForList(CHILDREN_ACCOUNTS,
        new String[]{accountId}, String.class);
    account.setChildren(childrenAccounts);
    return account;
  }
}
