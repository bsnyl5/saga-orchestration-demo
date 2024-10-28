package vn.ghtk.training.microservices.orderbc.saga.state;

import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.dto.OrderApprovalResultSagaInputContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;
import vn.ghtk.training.microservices.saga.domain.saga.StateName;

public class OrderApprovalRequestedState extends SagaState<OrderSaga, OrderApprovalResultSagaInputContent> {
    public OrderApprovalRequestedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    public SagaOutput process(SagaInput<OrderApprovalResultSagaInputContent> sagaInput) {
        if (sagaInput.content().isSuccess()) {
            // TODO
        } else {
            // TODO
        }
        return null;
    }

    @Override
    protected void initName() {
        this.stateName = new StateName("OrderApprovedState");
    }
}
