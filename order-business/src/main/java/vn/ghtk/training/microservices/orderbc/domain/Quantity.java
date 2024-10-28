package vn.ghtk.training.microservices.orderbc.domain;

public class Quantity {
    private final int value;

    public Quantity(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
