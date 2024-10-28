package vn.ghtk.training.microservices.inventorybc.port.dto;

import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class InventoryReservationConfirmationInput {
    private final SagaId sagaId;
    private final boolean isSuccess;

    public InventoryReservationConfirmationInput(SagaId sagaId, boolean isSuccess) {
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
