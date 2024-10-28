package vn.ghtk.training.microservices.orderbc.saga.state;

import vn.ghtk.training.microservices.orderbc.saga.dto.OrderCancelResultSagaInputContent;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;

public class OrderCancelRequestedState extends SagaState<OrderSaga, OrderCancelResultSagaInputContent> {
    public OrderCancelRequestedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    public SagaOutput process(SagaInput<OrderCancelResultSagaInputContent> sagaInput) {
        // TODO
        return null;
    }

    @Override
    protected void initName() {
        // TODO
    }
}
