package milanogc.ddd.domain;

import java.io.Serializable;
import java.util.Date;

public interface Event extends Serializable {
  Date occurredOn();
}
