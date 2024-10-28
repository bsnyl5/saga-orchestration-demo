package vn.ghtk.training.microservices.orderbc.port.dto;

import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class InventoryReservationConfirmationResultInput {
    private final SagaId sagaId;
    private final boolean isSuccess;

    public InventoryReservationConfirmationResultInput(SagaId sagaId, boolean isSuccess) {
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
