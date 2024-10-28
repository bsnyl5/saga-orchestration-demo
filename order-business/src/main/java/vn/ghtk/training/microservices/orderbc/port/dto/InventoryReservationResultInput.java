package vn.ghtk.training.microservices.orderbc.port.dto;

import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public final class InventoryReservationResultInput {
    private final SagaId sagaId;

    private final boolean isSuccess;

    public InventoryReservationResultInput(SagaId sagaId, boolean isSuccess) {
        this.sagaId = sagaId;
        this.isSuccess = isSuccess;
    }

    public SagaId sagaId() {
        return this.sagaId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
