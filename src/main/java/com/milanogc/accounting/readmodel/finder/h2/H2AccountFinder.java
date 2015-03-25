package com.milanogc.accounting.readmodel.finder.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class H2AccountFinder {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public H2AccountFinder(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Map<String, Object>> allAccounts() {
    return jdbcTemplate().queryForList("SELECT * FROM ACCOUNT");
  }

  private JdbcTemplate jdbcTemplate() {
    return this.jdbcTemplate;
  }
}
