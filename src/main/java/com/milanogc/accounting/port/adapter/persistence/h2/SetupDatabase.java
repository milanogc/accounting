package com.milanogc.accounting.port.adapter.persistence.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SetupDatabase {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public SetupDatabase(JdbcTemplate jdbcTemplate) {
    super();
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setup() {
    createAccountTable();
    createPostingTable();
    createEntryTable();
  }

  private void createAccountTable() {
    jdbcTemplate.execute("DROP TABLE ACCOUNT IF EXISTS");
    jdbcTemplate.execute(
        "CREATE TABLE ACCOUNT("
        + "ID CHAR(36) PRIMARY KEY,"
        + "NAME VARCHAR(255) UNIQUE,"
        + "CREATED_ON DATETIME,"
        + "DESCRIPTION VARCHAR(255),"
        + "PARENT_ACCOUNT_ID CHAR(36),"
        + "FOREIGN KEY(PARENT_ACCOUNT_ID) REFERENCES ACCOUNT(ID))");
  }

  private void createPostingTable() {
    jdbcTemplate.execute("DROP TABLE POSTING IF EXISTS");
    jdbcTemplate.execute(
        "CREATE TABLE POSTING("
        + "ID CHAR(36) PRIMARY KEY,"
        + "OCCURRED_ON DATETIME,"
        + "DESCRIPTION VARCHAR(255))");
  }

  private void createEntryTable() {
    jdbcTemplate.execute("DROP TABLE POSTING_ENTRY IF EXISTS");
    jdbcTemplate.execute(
        "CREATE TABLE POSTING_ENTRY("
        + "POSTING_ID CHAR(36),"
        + "ACCOUNT_ID CHAR(36),"
        + "AMOUNT DECIMAL(19, 4),"
        + "FOREIGN KEY(POSTING_ID) REFERENCES POSTING(ID),"
        + "FOREIGN KEY(ACCOUNT_ID) REFERENCES ACCOUNT(ID),"
        + "PRIMARY KEY(POSTING_ID, ACCOUNT_ID))");
  }
}
