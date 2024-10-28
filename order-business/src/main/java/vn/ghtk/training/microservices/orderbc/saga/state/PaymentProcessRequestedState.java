package vn.ghtk.training.microservices.orderbc.saga.state;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventType;
import vn.ghtk.training.microservices.commons.event.IssuerId;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.dto.PaymentProcessResultSagaInputContent;
import vn.ghtk.training.microservices.orderbc.saga.event.InventoryReservationConfirmationRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.event.InventoryReservationRejectionRequestedEventContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;
import vn.ghtk.training.microservices.saga.domain.saga.StateName;

import java.util.Date;

public class PaymentProcessRequestedState extends SagaState<OrderSaga, PaymentProcessResultSagaInputContent> {
    public PaymentProcessRequestedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    public SagaOutput process(SagaInput<PaymentProcessResultSagaInputContent> sagaInput) {
        if (sagaInput.content().isSuccess()) {
            DomainEvent<InventoryReservationConfirmationRequestedEventContent> event = this.generateInventoryReservationConfirmationRequestedEvent(sagaInput);

            return new SagaOutput(
                    sagaInput,
                    this.saga,
                    event,
                    new InventoryReservationConfirmationRequestedState(this.saga)
            );
        } else {
            DomainEvent<InventoryReservationRejectionRequestedEventContent> event = this.generateInventoryReservationRejectionRequestedEvent(sagaInput);
            return new SagaOutput(
                    sagaInput,
                    this.saga,
                    event,
                    new InventoryReservationRejectionRequestedState(this.saga)
            );
        }

    }

    private DomainEvent<InventoryReservationRejectionRequestedEventContent> generateInventoryReservationRejectionRequestedEvent(SagaInput<PaymentProcessResultSagaInputContent> sagaInput) {
        // TODO
        return null;
    }

    private DomainEvent<InventoryReservationConfirmationRequestedEventContent> generateInventoryReservationConfirmationRequestedEvent(SagaInput<PaymentProcessResultSagaInputContent> sagaInput) {
        InventoryReservationConfirmationRequestedEventContent eventContent = new InventoryReservationConfirmationRequestedEventContent();
        eventContent.setSagaId(this.saga.id());

        return new DomainEvent<>(
                new AggregateId(this.saga.id().value()),
                new EventType("InventoryReservationConfirmationRequest_Saga"),
                new IssuerId("Order Saga"),
                new Date(),
                eventContent
        );
    }

    @Override
    protected void initName() {
        this.stateName = new StateName("PaymentProcessPendingState");
    }
}
