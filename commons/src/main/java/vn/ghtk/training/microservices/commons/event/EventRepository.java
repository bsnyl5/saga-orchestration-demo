package vn.ghtk.training.microservices.commons.event;

import java.util.List;

public interface EventRepository {
    <T extends EventContent> void save(DomainEvent<?> orderCreatedEvent);

    void saveAll(List<DomainEvent<?>> domainEvents);

    List<DomainEvent<?>> readAll();
}
