package vn.ghtk.training.microservices.orderbc.saga.dto;

import vn.ghtk.training.microservices.orderbc.domain.Order;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInputContent;

public class InventoryReservationResultSagaInputContent extends SagaInputContent {
    private boolean isReservationSuccess;
    private Order order;

    public InventoryReservationResultSagaInputContent(Order order, boolean isReservationSuccess) {
        super();
        this.order = order;
        this.isReservationSuccess = isReservationSuccess;
    }

    public Order order() {
        return order;
    }
    public boolean isReservationSuccess() {
        return isReservationSuccess;
    }
}
