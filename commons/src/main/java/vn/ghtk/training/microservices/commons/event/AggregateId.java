package vn.ghtk.training.microservices.commons.event;

import java.util.Objects;

public final class AggregateId {
    private final String value;

    public AggregateId(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateId that = (AggregateId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "AggregateId{" +
                "value='" + value + '\'' +
                '}';
    }
}
