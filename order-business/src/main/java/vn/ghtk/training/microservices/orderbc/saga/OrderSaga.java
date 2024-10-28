package vn.ghtk.training.microservices.orderbc.saga;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.orderbc.saga.state.OrderCreatedState;
import vn.ghtk.training.microservices.saga.domain.saga.Saga;


public class OrderSaga extends Saga {

    public OrderSaga(AggregateId aggregateId) {
        super(aggregateId);
        this.state = new OrderCreatedState(this);
    }
}
