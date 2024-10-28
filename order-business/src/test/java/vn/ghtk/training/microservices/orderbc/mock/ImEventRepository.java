package vn.ghtk.training.microservices.orderbc.mock;

import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.commons.event.EventRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImEventRepository implements EventRepository {
    private final Map<String, DomainEvent<?>> eventStore = new HashMap<>();

    @Override
    public <T extends EventContent> void save(DomainEvent<?> event) {
        eventStore.put(event.id().value(), event);
    }

    @Override
    public void saveAll(List<DomainEvent<?>> events) {
        for (DomainEvent<?> event : events) {
            eventStore.put(event.id().value(), event);
        }
    }

    @Override
    public List<DomainEvent<?>> readAll() {
        return eventStore.values().stream().collect(Collectors.toList());
    }
}
