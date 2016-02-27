package com.milanogc.accounting.domain.account;

import com.milanogc.ddd.domain.ValueObject;

import java.util.Objects;

public class PostingId extends ValueObject {

  private String id;

  public PostingId(String id) {
    super();
    setId(id);
  }

  public String id() {
    return this.id;
  }

  private void setId(String id) {
    this.id = Objects.requireNonNull(id, "The id must be provided.");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PostingId typedObject = (PostingId) o;
    return this.id().equals(typedObject.id());
  }

  @Override
  public int hashCode() {
    return this.id().hashCode();
  }
}
