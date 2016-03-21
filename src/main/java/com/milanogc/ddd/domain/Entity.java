package com.milanogc.ddd.domain;

public class Entity {
  private long id;
  private int version;

  protected Entity() {
    super();
    this.setId(-1);
    this.setVersion(-1);
  }

  protected long id() {
    return this.id;
  }

  private void setId(long id) {
    this.id = id;
  }

  protected int version() {
    return this.version;
  }

  private void setVersion(int version) {
    this.version = version;
  }
}
