package vn.ghtk.training.microservices.orderbc.saga.event;

import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class InventoryReservationConfirmationRequestedEventContent implements EventContent {
    private SagaId sagaId;

    public void setSagaId(SagaId sagaId) {
        this.sagaId = sagaId;
    }

    public SagaId sagaId() {
        return sagaId;
    }
}
