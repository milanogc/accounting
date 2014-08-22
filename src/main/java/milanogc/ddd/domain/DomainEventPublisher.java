package milanogc.ddd.domain;

public interface DomainEventPublisher {
  void publish(Event event);
}
