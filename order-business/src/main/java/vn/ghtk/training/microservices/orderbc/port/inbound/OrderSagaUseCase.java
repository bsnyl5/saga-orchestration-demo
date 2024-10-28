package vn.ghtk.training.microservices.orderbc.port.inbound;

import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.orderbc.domain.OrderCreatedEventContent;
import vn.ghtk.training.microservices.orderbc.port.dto.InventoryReservationConfirmationResultInput;
import vn.ghtk.training.microservices.orderbc.port.dto.InventoryReservationResultInput;
import vn.ghtk.training.microservices.orderbc.port.dto.PaymentProcessResultInput;
import vn.ghtk.training.microservices.saga.domain.saga.InvalidSagaInputException;

public interface OrderSagaUseCase {
    void handleOrderCreationResult(DomainEvent<OrderCreatedEventContent> orderCreatedEvent) throws InvalidSagaInputException;
    void handleInventoryReservationResult(InventoryReservationResultInput input) throws InvalidSagaInputException;
    void handlePaymentProcessResult(PaymentProcessResultInput paymentProcessedEvent) throws InvalidSagaInputException;
    void handleInventoryReservationConfirmationResult(InventoryReservationConfirmationResultInput input) throws InvalidSagaInputException;

    void handlePaymentFailed(DomainEvent<?> paymentFailedEvent);
    void handleInventoryReservationRejected(DomainEvent<?> inventoryReservationRejectedEvent);
}
