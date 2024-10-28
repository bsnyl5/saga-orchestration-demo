package vn.ghtk.training.microservices.saga.domain.saga;

public final class StateName {
    private final String value;

    public StateName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "StateName{" +
                "value='" + value + '\'' +
                '}';
    }
}
