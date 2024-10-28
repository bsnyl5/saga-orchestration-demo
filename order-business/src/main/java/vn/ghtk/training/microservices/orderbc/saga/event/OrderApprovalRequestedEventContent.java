package vn.ghtk.training.microservices.orderbc.saga.event;

import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.orderbc.domain.OrderId;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

public class OrderApprovalRequestedEventContent implements EventContent {
    private final OrderId orderId;
    private final SagaId sagaId;

    public OrderApprovalRequestedEventContent(OrderId orderId, SagaId sagaId) {
        this.orderId = orderId;
        this.sagaId = sagaId;
    }

    public OrderId orderId() {
        return orderId;
    }

    public SagaId sagaId() {
        return sagaId;
    }
}
