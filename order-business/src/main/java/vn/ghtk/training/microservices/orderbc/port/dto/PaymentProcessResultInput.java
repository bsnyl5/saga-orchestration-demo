package vn.ghtk.training.microservices.orderbc.port.dto;

import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class PaymentProcessResultInput {
    private final SagaId sagaId;
    private final boolean success;

    public PaymentProcessResultInput(SagaId sagaId, boolean success) {
        this.sagaId = sagaId;
        this.success = success;
    }

    public SagaId sagaId() {
        return sagaId;
    }

    public boolean isSuccess() {
        return success;
    }
}
