package com.milanogc.accounting.domain.account;

import java.math.BigDecimal;
import java.util.Objects;

// value object
public class Amount {

  public static Amount ZERO = new Amount(BigDecimal.ZERO);
  public static Amount ONE = new Amount(BigDecimal.ONE);
  private BigDecimal value;

  public Amount(BigDecimal value) {
    super();
    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }

  public boolean isZero() {
    return value.equals(BigDecimal.ZERO);
  }

  public Amount plus(Amount otherAmount) {
    return new Amount(value().add(otherAmount.value()));
  }

  public Amount negate() {
    return new Amount(value().negate());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Amount typedObject = (Amount) o;
    return Objects.equals(value(), typedObject.value());
  }

  @Override
  public int hashCode() {
    return Objects.hash(value());
  }
}
