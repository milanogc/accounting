package com.milanogc.accounting.readmodel.finder.postgres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.milanogc.accounting.readmodel.finder.postgres.dto.Entries;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Entry;

@Repository
public class PostgresEntryFinder {

  private static String ENTRIES_OF_ACCOUNT =
      "SELECT " +
        "CONCAT_WS('-', PE.POSTING_ID, PE.ACCOUNT_ID) AS ID, " + // fake id
        "PE.ACCOUNT_ID AS ACCOUNT, " +
        "PE.AMOUNT AS AMOUNT, " +
        "P.OCCURRED_ON AS OCCURRED_ON, " +
        "P.DESCRIPTION AS DESCRIPTION, " +
        "SUM (PE.AMOUNT) OVER (ORDER BY PE.INSERTION_ORDER) AS SUM " +
      "FROM POSTING_ENTRY PE " +
      "INNER JOIN POSTING P " +
        "ON PE.POSTING_ID = P.ID " +
      "INNER JOIN ACCOUNT_CLOSURE AC " +
        "ON PE.ACCOUNT_ID = AC.DESCENDANT_ACCOUNT_ID " +
      "WHERE AC.ANCESTOR_ACCOUNT_ID = ?" +
      "ORDER BY PE.INSERTION_ORDER";

  private static String BALANCES =
      "SELECT " +
        "AC.ANCESTOR_ACCOUNT_ID AS ID, " +
        "SUM(PE.AMOUNT) AS AMOUNT " +
      "FROM POSTING_ENTRY PE " +
      "INNER JOIN ACCOUNT_CLOSURE AC " +
        "ON PE.ACCOUNT_ID = AC.DESCENDANT_ACCOUNT_ID " +
      "GROUP BY AC.ANCESTOR_ACCOUNT_ID";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PostgresEntryFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Entries entriesOfAccount(String accountId) {
    List<Entry> entries = jdbcTemplate.query(ENTRIES_OF_ACCOUNT, new String[]{accountId},
        new BeanPropertyRowMapper<Entry>(Entry.class));
    return new Entries(entries);
  }
}
