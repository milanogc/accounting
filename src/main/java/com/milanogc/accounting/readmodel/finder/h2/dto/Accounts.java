package com.milanogc.accounting.readmodel.finder.h2.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "accounts")
public class Accounts extends ArrayList<Account> {

  private static final long serialVersionUID = 1L;
  
  public Accounts(Collection<Account> accounts) {
    addAll(accounts);
  }

}
