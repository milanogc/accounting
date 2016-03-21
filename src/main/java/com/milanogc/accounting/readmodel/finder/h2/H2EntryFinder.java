package com.milanogc.accounting.readmodel.finder.h2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.milanogc.accounting.readmodel.finder.h2.dto.Entries;
import com.milanogc.accounting.readmodel.finder.h2.dto.Entry;

@Repository
public class H2EntryFinder {

  private static String ENTRIES_OF_ACCOUNT =
      "SELECT " +
        "CONCAT(PE.POSTING_ID, '-', PE.ACCOUNT_ID) AS ID, " + // fake id
        "PE.ACCOUNT_ID AS ACCOUNT, " +
        "PE.AMOUNT AS AMOUNT, " +
        "P.OCCURRED_ON AS OCCURRED_ON " +
      "FROM POSTING_ENTRY PE " +
      "INNER JOIN POSTING P " +
        "ON PE.POSTING_ID = P.ID " +
      "INNER JOIN ACCOUNT_CLOSURE AC " +
        "ON PE.ACCOUNT_ID = AC.DESCENDANT_ACCOUNT_ID " +
      "WHERE AC.ANCESTOR_ACCOUNT_ID = ?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public H2EntryFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Entries entriesOfAccount(String accountId) {
    List<Entry> entries = jdbcTemplate.query(ENTRIES_OF_ACCOUNT, new String[]{accountId},
        new BeanPropertyRowMapper<Entry>(Entry.class));
    return new Entries(entries);
  }
}
