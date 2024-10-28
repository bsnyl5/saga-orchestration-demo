package vn.ghtk.training.microservices.commons.event;

public class IssuerId {
    private final String value;

    public IssuerId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
