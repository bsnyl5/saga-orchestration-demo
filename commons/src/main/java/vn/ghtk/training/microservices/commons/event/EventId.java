package vn.ghtk.training.microservices.commons.event;

import java.util.Objects;

public final class EventId {
    private final String value;

    public EventId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId = (EventId) o;
        return Objects.equals(value, eventId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "EventId{" +
                "value='" + value + '\'' +
                '}';
    }
}
