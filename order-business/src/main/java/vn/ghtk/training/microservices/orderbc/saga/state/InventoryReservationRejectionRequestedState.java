package vn.ghtk.training.microservices.orderbc.saga.state;

import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.dto.InventoryReservationRejectionRequestedSagaInputContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;
import vn.ghtk.training.microservices.saga.domain.saga.StateName;

public class InventoryReservationRejectionRequestedState
        extends SagaState<OrderSaga, InventoryReservationRejectionRequestedSagaInputContent> {

    public InventoryReservationRejectionRequestedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    public SagaOutput process(SagaInput<InventoryReservationRejectionRequestedSagaInputContent> sagaInput) {
        // TODO
        return null;
    }

    @Override
    protected void initName() {
        this.stateName = new StateName("InventoryReservationRejectionPendingState");
    }
}

