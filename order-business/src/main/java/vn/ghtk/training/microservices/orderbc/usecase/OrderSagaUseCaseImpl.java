package vn.ghtk.training.microservices.orderbc.usecase;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.orderbc.domain.Order;
import vn.ghtk.training.microservices.orderbc.domain.OrderCreatedEventContent;
import vn.ghtk.training.microservices.orderbc.domain.OrderId;
import vn.ghtk.training.microservices.orderbc.domain.OrderRepository;
import vn.ghtk.training.microservices.orderbc.port.dto.InventoryReservationConfirmationResultInput;
import vn.ghtk.training.microservices.orderbc.port.dto.InventoryReservationResultInput;
import vn.ghtk.training.microservices.orderbc.port.dto.PaymentProcessResultInput;
import vn.ghtk.training.microservices.orderbc.port.inbound.OrderSagaUseCase;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.OrderSagaOrchestrator;
import vn.ghtk.training.microservices.orderbc.saga.dto.*;
import vn.ghtk.training.microservices.saga.domain.saga.*;

import java.util.Optional;

public class OrderSagaUseCaseImpl implements OrderSagaUseCase {
    private final OrderSagaOrchestrator sagaOrchestrator;
    private final OrderRepository orderRepository;
    private final SagaRepository sagaRepository;

    public OrderSagaUseCaseImpl(OrderSagaOrchestrator sagaOrchestrator, OrderRepository orderRepository, SagaRepository sagaRepository) {
        this.sagaOrchestrator = sagaOrchestrator;
        this.orderRepository = orderRepository;
        this.sagaRepository = sagaRepository;
    }

    @Override
    public void handleOrderCreationResult(DomainEvent<OrderCreatedEventContent> orderCreatedEvent) throws InvalidSagaInputException {
        SagaInput<OrderCreatedSagaInputContent> sagaInput = createOrderCreatedSagaInput(orderCreatedEvent);
        sagaOrchestrator.handle(sagaInput);
    }

    @Override
    public void handleInventoryReservationResult(InventoryReservationResultInput input) throws InvalidSagaInputException {
        SagaInput<InventoryReservationResultSagaInputContent> sagaInput = createInventoryReservationResultSagaInput(input);
        sagaOrchestrator.handle(sagaInput);
    }

    @Override
    public void handlePaymentProcessResult(PaymentProcessResultInput input) throws InvalidSagaInputException {
        SagaInput<PaymentProcessResultSagaInputContent> sagaInput = createPaymentProcessResultSagaInput(input);
        sagaOrchestrator.handle(sagaInput);
    }

    @Override
    public void handleInventoryReservationConfirmationResult(InventoryReservationConfirmationResultInput input) throws InvalidSagaInputException {
        SagaInput<InventoryReservationConfirmationResultSagaInputContent> sagaInput = createInventoryReservationConfirmationResultSagaInput(input);
        sagaOrchestrator.handle(sagaInput);
    }

    @Override
    public void handlePaymentFailed(DomainEvent<?> paymentFailedEvent) {
        SagaInput<PaymentFailedSagaInputContent> sagaInput = createPaymentFailedSagaInput(paymentFailedEvent);
        sagaOrchestrator.handle(sagaInput);
    }

    @Override
    public void handleInventoryReservationRejected(DomainEvent<?> inventoryReservationRejectedEvent) {
        // TODO
    }

    private SagaInput<OrderCreatedSagaInputContent> createOrderCreatedSagaInput(DomainEvent<OrderCreatedEventContent> orderCreatedEvent) throws InvalidSagaInputException {
        Order order = getOrder(orderCreatedEvent.aggregateId());
        OrderCreatedSagaInputContent inputContent = new OrderCreatedSagaInputContent(order);

        return new SagaInput<>(orderCreatedEvent.aggregateId(), inputContent);
    }

    private SagaInput<InventoryReservationResultSagaInputContent> createInventoryReservationResultSagaInput(InventoryReservationResultInput input) throws InvalidSagaInputException {
        OrderSaga saga = getSaga(input.sagaId());
        Order order = getOrder(saga.aggregateId());
        InventoryReservationResultSagaInputContent inputContent = new InventoryReservationResultSagaInputContent(order, input.isSuccess());

        return new SagaInput<>(new AggregateId(order.id().value()), inputContent);
    }

    private SagaInput<PaymentProcessResultSagaInputContent> createPaymentProcessResultSagaInput(PaymentProcessResultInput input) throws InvalidSagaInputException {
        OrderSaga saga = getSaga(input.sagaId());
        Order order = getOrder(saga.aggregateId());
        PaymentProcessResultSagaInputContent inputContent = new PaymentProcessResultSagaInputContent(order, input.isSuccess());

        return new SagaInput<>(
                saga.aggregateId(),
                inputContent
        );
    }

    private SagaInput<InventoryReservationConfirmationResultSagaInputContent> createInventoryReservationConfirmationResultSagaInput(InventoryReservationConfirmationResultInput input) throws InvalidSagaInputException {
        OrderSaga saga = getSaga(input.sagaId());
        Order order = getOrder(saga.aggregateId());
        InventoryReservationConfirmationResultSagaInputContent inputContent = new InventoryReservationConfirmationResultSagaInputContent(order, input.isSuccess());

        return new SagaInput<>(
                saga.aggregateId(),
                inputContent
        );
    }

    private SagaInput<PaymentFailedSagaInputContent> createPaymentFailedSagaInput(DomainEvent<?> paymentFailedEvent) {
        // TODO
        return null;
    }

    private Order getOrder(AggregateId aggregateId) throws InvalidSagaInputException {
        Optional<Order> order = orderRepository.read(new OrderId(aggregateId.value()));
        if (!order.isPresent()) {
            throw new InvalidSagaInputException("Order not found.");
        }

        return order.get();
    }

    private OrderSaga getSaga(SagaId sagaId) throws InvalidSagaInputException {
        Optional<OrderSaga> saga = sagaRepository.read(new SagaId(sagaId.value()));
        if (!saga.isPresent()) {
            throw new InvalidSagaInputException("Saga not found.");
        }

        return saga.get();
    }
}
