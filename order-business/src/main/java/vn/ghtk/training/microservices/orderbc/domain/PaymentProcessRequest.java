package vn.ghtk.training.microservices.orderbc.domain;

import java.math.BigDecimal;

public class PaymentProcessRequest {
    private final CustomerId customerId;
    private final BigDecimal amount;


    public PaymentProcessRequest(CustomerId customerId, BigDecimal amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public BigDecimal amount() {
        return amount;
    }
}
