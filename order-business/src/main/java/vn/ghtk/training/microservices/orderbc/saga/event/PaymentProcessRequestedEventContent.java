package vn.ghtk.training.microservices.orderbc.saga.event;

import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.orderbc.domain.CustomerId;
import vn.ghtk.training.microservices.orderbc.domain.PaymentProcessRequest;
import vn.ghtk.training.microservices.orderbc.domain.Price;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

import java.math.BigDecimal;

public class PaymentProcessRequestedEventContent implements EventContent {
    private final SagaId sagaId;
    private final CustomerId customerId;
    private final BigDecimal amount;

    public PaymentProcessRequestedEventContent(SagaId sagaId, PaymentProcessRequest paymentProcessRequest) {
        this.sagaId = sagaId;
        this.customerId = paymentProcessRequest.customerId();
        this.amount = paymentProcessRequest.amount();
    }

    public SagaId sagaId() {
        return sagaId;
    }
}
