package vn.ghtk.training.microservices.orderbc.domain;

import vn.ghtk.training.microservices.commons.ddd.Id;

import java.util.Objects;

public class OrderId implements Id {
    private final String value;

    public OrderId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(value, orderId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "OrderId{" +
                "value='" + value + '\'' +
                '}';
    }
}
