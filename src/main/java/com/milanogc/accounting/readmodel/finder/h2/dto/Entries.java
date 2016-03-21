package com.milanogc.accounting.readmodel.finder.h2.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "entries")
public class Entries extends ArrayList<Entry> {

  private static final long serialVersionUID = 1L;

  public Entries() {
    super();
  }

  public Entries(Collection<Entry> entries) {
    this();
    addAll(entries);
  }

}
