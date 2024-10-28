package vn.ghtk.training.microservices.orderbc.domain;

import vn.ghtk.training.microservices.commons.event.EventContent;

import java.util.List;

public class OrderCreatedEventContent implements EventContent {
    private final OrderId orderId;
    private final OrderStatus orderStatus;
    private final CustomerId customerId;
    private final List<Item> items;

    public OrderCreatedEventContent(OrderId orderId, OrderStatus orderStatus, CustomerId customerId, List<Item> items) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderCreatedEventContent{" +
                "orderId=" + orderId +
                ", orderStatus=" + orderStatus +
                ", customerId=" + customerId +
                ", items=" + items +
                '}';
    }
}
