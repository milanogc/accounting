package com.milanogc.accounting.domain.account;

import java.math.BigDecimal;
import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.milanogc.ddd.domain.ValueObject;

// value object
public class Amount extends ValueObject {

  public static final Amount ZERO = new Amount(BigDecimal.ZERO);
  public static final Amount ONE = new Amount(BigDecimal.ONE);

  private BigDecimal value;

  private Amount() {
    super();
  }

  public Amount(BigDecimal value) {
    this();
    this.value = value;
  }

  public BigDecimal value() {
    return this.value;
  }

  public boolean isZero() {
    return this.equals(ZERO);
  }

  public Amount plus(Amount otherAmount) {
    return new Amount(this.value().add(otherAmount.value()));
  }

  public Amount negate() {
    return new Amount(this.value().negate());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Amount typedObject = (Amount) o;
    return this.value().compareTo(typedObject.value()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value());
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(this.value())
        .toString();
  }
}
