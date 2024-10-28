package vn.ghtk.training.microservices.saga.domain.saga;

import java.util.Objects;

public final class SagaId {
    private final String value;

    public SagaId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SagaId sagaId = (SagaId) o;
        return Objects.equals(value, sagaId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "SagaId{" +
                "value='" + value + '\'' +
                '}';
    }
}
