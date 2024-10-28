package vn.ghtk.training.microservices.inventorybc.port.dto;

import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class InventoryReservationInput {
    private final SagaId sagaId;

    public InventoryReservationInput(SagaId sagaId) {
        this.sagaId = sagaId;
    }

    public SagaId sagaId() {
        return sagaId;
    }
}
