package vn.ghtk.training.microservices.orderbc.domain;

import java.math.BigDecimal;

public class Price {
    private final BigDecimal value;

    public Price(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal value() {
        return value;
    }

    public Price multiple(Quantity quantity) {
        return new Price(value.multiply(new BigDecimal(quantity.value())));
    }

}
