package milanogc.accounting.account;

import java.util.Objects;

// value object
public class AccountId {
    private String id;

    public AccountId(String id) {
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

        AccountId typedObject = (AccountId) o;
        return id().equals(typedObject.id());
    }

    @Override
    public int hashCode() {
        return id().hashCode();
    }
}
