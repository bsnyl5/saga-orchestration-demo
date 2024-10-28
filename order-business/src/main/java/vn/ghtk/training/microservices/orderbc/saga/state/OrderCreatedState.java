package vn.ghtk.training.microservices.orderbc.saga.state;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventType;
import vn.ghtk.training.microservices.commons.event.IssuerId;
import vn.ghtk.training.microservices.orderbc.saga.dto.OrderCreatedSagaInputContent;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.event.InventoryReservationRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.event.ProductReservation;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOutput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaState;
import vn.ghtk.training.microservices.saga.domain.saga.StateName;

import java.util.Date;
import java.util.List;

public class OrderCreatedState extends SagaState<OrderSaga, OrderCreatedSagaInputContent> {
    public OrderCreatedState(OrderSaga saga) {
        super(saga);
    }

    @Override
    public SagaOutput process(SagaInput<OrderCreatedSagaInputContent> sagaInput) {
        DomainEvent<InventoryReservationRequestedEventContent> inventoryReservationRequestedEvent = this.generateEvent(sagaInput);
        return new SagaOutput(
                sagaInput,
                this.saga,
                inventoryReservationRequestedEvent,
                new InventoryReservationRequestedState(this.saga)
        );
    }

    @Override
    protected void initName() {
        this.stateName = new StateName("OrderCreatedState");
    }

    private DomainEvent<InventoryReservationRequestedEventContent> generateEvent(SagaInput<OrderCreatedSagaInputContent> sagaInput) {
        InventoryReservationRequestedEventContent eventContent = new InventoryReservationRequestedEventContent();
        List<ProductReservation> productReservations = sagaInput.content().order().createProductReservationRequest();
        eventContent.setSagaId(this.saga.id());

        return new DomainEvent<>(
            new AggregateId(this.saga.id().value()),
            new EventType("InventoryReservationRequest_Saga"),
            new IssuerId("Order Saga"),
            new Date(),
            eventContent
        );
    }
}
