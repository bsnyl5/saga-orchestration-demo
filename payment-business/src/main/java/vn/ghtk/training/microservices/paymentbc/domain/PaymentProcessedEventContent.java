package vn.ghtk.training.microservices.paymentbc.domain;

import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class PaymentProcessedEventContent implements EventContent {
    private final SagaId sagaId;
    private final boolean isSuccess;

    public PaymentProcessedEventContent(SagaId sagaId, boolean isSuccess) {
        this.sagaId = sagaId;
        this.isSuccess = isSuccess;
    }

    public SagaId sagaId() {
        return sagaId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
