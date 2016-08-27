package com.milanogc.accounting.readmodel.finder.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.milanogc.accounting.readmodel.finder.postgres.dto.Posting;

@Repository
public class PostgresPostingFinder {
  private static final String SINGLE_POST =
      "SELECT " +
        "ID, " +
        "OCCURRED_ON, " +
        "DESCRIPTION " +
      "FROM POSTING " +
      "WHERE ID = ?";

  /*private static final String ENTRIES =
      "SELECT " +
        "CONCAT_WS('-', PE.POSTING_ID, PE.ACCOUNT_ID) AS ID " + // fake id
      "FROM POSTING_ENTRY PE " +
      "WHERE PE.POSTING_ID = ?";*/

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PostgresPostingFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Posting posting(String postingId) {
    Posting posting = jdbcTemplate.queryForObject(SINGLE_POST, new String[]{postingId},
        new BeanPropertyRowMapper<Posting>(Posting.class));
    /*List<String> entries = jdbcTemplate.queryForList(ENTRIES,
        new String[]{postingId}, String.class);
    posting.setEntries(new Entries());*/
    return posting;
  }
}
