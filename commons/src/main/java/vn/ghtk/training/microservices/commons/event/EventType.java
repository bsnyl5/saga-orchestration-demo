package vn.ghtk.training.microservices.commons.event;

import java.util.Objects;

public final class EventType {
    private final String value;

    public EventType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventType eventType = (EventType) o;
        return Objects.equals(value, eventType.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "EventType{" +
                "value='" + value + '\'' +
                '}';
    }
}
