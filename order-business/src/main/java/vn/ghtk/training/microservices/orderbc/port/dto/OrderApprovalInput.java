package vn.ghtk.training.microservices.orderbc.port.dto;

import vn.ghtk.training.microservices.orderbc.domain.OrderId;

public class OrderApprovalInput {
    private final OrderId orderId;

    public OrderApprovalInput(OrderId orderId) {
        this.orderId = orderId;
    }

    public OrderId orderId() {
        return orderId;
    }
}
