package vn.ghtk.training.microservices.inventorybc.domain;

import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class InventoryReservationProcessedEventContent implements EventContent {
    private final SagaId sagaId;
    private OperationType operationType;
    private final boolean isSuccess;

    public InventoryReservationProcessedEventContent(SagaId sagaId, OperationType operationType, boolean isSuccess) {
        this.sagaId = sagaId;
        this.operationType = operationType;
        this.isSuccess = isSuccess;
    }

    public SagaId sagaId() {
        return sagaId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public OperationType operationType() {
        return operationType;
    }
}
