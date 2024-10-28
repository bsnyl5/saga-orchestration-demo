package vn.ghtk.training.microservices.orderbc.saga.state;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventType;
import vn.ghtk.training.microservices.commons.event.IssuerId;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.dto.InventoryReservationConfirmationResultSagaInputContent;
import vn.ghtk.training.microservices.orderbc.saga.event.OrderApprovalRequestedEventContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;
import vn.ghtk.training.microservices.saga.domain.saga.StateName;

import java.util.Date;

public class InventoryReservationConfirmationRequestedState extends SagaState<OrderSaga, InventoryReservationConfirmationResultSagaInputContent> {
    public InventoryReservationConfirmationRequestedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    public SagaOutput process(SagaInput<InventoryReservationConfirmationResultSagaInputContent> sagaInput) {
        if (sagaInput.content().isSuccess()) {
            DomainEvent<OrderApprovalRequestedEventContent> event = this.generateOrderApprovalRequestedEvent(sagaInput);

            return new SagaOutput(
                    sagaInput,
                    this.saga,
                    event,
                    new OrderApprovalRequestedState(this.saga)
            );
        } else {
            // TODO
            return null;
        }
    }

    private DomainEvent<OrderApprovalRequestedEventContent> generateOrderApprovalRequestedEvent(SagaInput<InventoryReservationConfirmationResultSagaInputContent> sagaInput) {
        OrderApprovalRequestedEventContent eventContent = new OrderApprovalRequestedEventContent(
                sagaInput.content().order().id(),
                this.saga.id()
        );

        return new DomainEvent<>(
                new AggregateId(this.saga.id().value()),
                new EventType("OrderApprovalRequest_Saga"),
                new IssuerId("Order Saga"),
                new Date(),
                eventContent
        );
    }

    @Override
    protected void initName() {
        this.stateName = new StateName("InventoryReservationConfirmationPendingState");
    }
}
