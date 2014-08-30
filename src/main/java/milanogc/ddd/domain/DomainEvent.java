package milanogc.ddd.domain;

import java.io.Serializable;
import java.util.Date;

public interface DomainEvent extends Serializable {

  Date occurredOn();
}
