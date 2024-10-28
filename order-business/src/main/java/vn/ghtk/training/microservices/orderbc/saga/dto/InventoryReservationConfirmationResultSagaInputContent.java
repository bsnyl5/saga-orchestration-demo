package vn.ghtk.training.microservices.orderbc.saga.dto;

import vn.ghtk.training.microservices.orderbc.domain.Order;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInputContent;

public class InventoryReservationConfirmationResultSagaInputContent extends SagaInputContent {
    private final Order order;
    private final boolean isSuccess;

    public InventoryReservationConfirmationResultSagaInputContent(Order order, boolean isSuccess) {
        this.order = order;
        this.isSuccess = isSuccess;
    }

    public Order order() {
        return order;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
