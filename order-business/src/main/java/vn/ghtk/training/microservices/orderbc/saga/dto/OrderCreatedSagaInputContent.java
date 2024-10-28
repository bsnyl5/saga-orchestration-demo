package vn.ghtk.training.microservices.orderbc.saga.dto;

import vn.ghtk.training.microservices.orderbc.domain.Order;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInputContent;

public class OrderCreatedSagaInputContent extends SagaInputContent {
    private Order order;

    public OrderCreatedSagaInputContent(Order order) {
        super();
        this.order = order;
    }

    public Order order() {
        return order;
    }
}
