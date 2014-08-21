package milanogc.accounting.domain.account;

import java.util.Objects;

// value object
public class PostingId {

  private String id;

  public PostingId(String id) {
    setId(id);
  }

  public String id() {
    return id;
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
    return id().equals(typedObject.id());
  }

  @Override
  public int hashCode() {
    return id().hashCode();
  }
}
