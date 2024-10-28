package vn.ghtk.training.microservices.orderbc.saga.state;


import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventType;
import vn.ghtk.training.microservices.commons.event.IssuerId;
import vn.ghtk.training.microservices.orderbc.saga.event.OrderCancelRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.event.PaymentProcessRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.dto.InventoryReservationResultSagaInputContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;
import vn.ghtk.training.microservices.saga.domain.saga.StateName;

import java.util.Date;

public class InventoryReservationRequestedState extends SagaState<OrderSaga, InventoryReservationResultSagaInputContent> {
    public InventoryReservationRequestedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    protected void initName() {
        this.stateName = new StateName("InventoryReservationPendingState");
    }

    @Override
    public SagaOutput process(SagaInput<InventoryReservationResultSagaInputContent> sagaInput) {
        if (sagaInput.content().isReservationSuccess()) {
            DomainEvent<PaymentProcessRequestedEventContent> event = this.generatePaymentRequestedEvent(sagaInput);

            return new SagaOutput(
                    sagaInput,
                    this.saga,
                    event,
                    new PaymentProcessRequestedState(this.saga)
            );
        } else {
            DomainEvent<OrderCancelRequestedEventContent> event = this.generateOrderCancelRequestedEvent(sagaInput);

            return new SagaOutput(
                    sagaInput,
                    this.saga,
                    event,
                    new OrderCancelRequestedState(this.saga)
            );
        }

    }

    private DomainEvent<OrderCancelRequestedEventContent> generateOrderCancelRequestedEvent(SagaInput<InventoryReservationResultSagaInputContent> sagaInput) {
        // TODO
        return null;
    }

    private DomainEvent<PaymentProcessRequestedEventContent> generatePaymentRequestedEvent(SagaInput<InventoryReservationResultSagaInputContent> sagaInput) {
        PaymentProcessRequestedEventContent eventContent = new PaymentProcessRequestedEventContent(
                this.saga.id(),
                sagaInput.content().order().createPaymentProcessRequest()
        );

        return new DomainEvent<>(new AggregateId(this.saga.id().value()),
                new EventType("PaymentProcessRequest_Saga"),
                new IssuerId("Order Saga"),
                new Date(),
                eventContent);
    }
}
