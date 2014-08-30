package milanogc.ddd.domain;

public interface DomainEventSubscriber<T> {

  public void handleEvent(final T aDomainEvent);

  public Class<T> subscribedToEventType();
}
