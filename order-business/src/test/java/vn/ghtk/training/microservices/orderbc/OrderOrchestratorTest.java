package vn.ghtk.training.microservices.orderbc;

import junit.framework.TestCase;
import vn.ghtk.training.microservices.commons.event.*;
import vn.ghtk.training.microservices.inventorybc.domain.InventoryReservationProcessedEventContent;
import vn.ghtk.training.microservices.inventorybc.domain.OperationType;
import vn.ghtk.training.microservices.inventorybc.port.dto.InventoryReservationInput;
import vn.ghtk.training.microservices.inventorybc.port.inbound.InventoryReservationUseCase;
import vn.ghtk.training.microservices.inventorybc.usecase.InventoryReservationUseCaseImpl;
import vn.ghtk.training.microservices.orderbc.domain.*;
import vn.ghtk.training.microservices.orderbc.mock.ImEventRepository;
import vn.ghtk.training.microservices.orderbc.mock.ImOrderRepository;
import vn.ghtk.training.microservices.orderbc.mock.ImProductRepository;
import vn.ghtk.training.microservices.orderbc.mock.ImSagaRepository;
import vn.ghtk.training.microservices.orderbc.port.dto.*;
import vn.ghtk.training.microservices.orderbc.port.inbound.OrderCreationUseCase;
import vn.ghtk.training.microservices.orderbc.port.inbound.OrderSagaUseCase;
import vn.ghtk.training.microservices.orderbc.saga.OrderSaga;
import vn.ghtk.training.microservices.orderbc.saga.OrderSagaOrchestrator;
import vn.ghtk.training.microservices.orderbc.saga.event.InventoryReservationConfirmationRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.event.InventoryReservationRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.event.OrderApprovalRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.saga.event.PaymentProcessRequestedEventContent;
import vn.ghtk.training.microservices.orderbc.usecase.OrderCreationUseCaseImpl;
import vn.ghtk.training.microservices.orderbc.usecase.OrderSagaUseCaseImpl;
import vn.ghtk.training.microservices.paymentbc.domain.PaymentProcessedEventContent;
import vn.ghtk.training.microservices.saga.domain.saga.InvalidSagaInputException;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOrchestrator;
import vn.ghtk.training.microservices.saga.domain.saga.SagaRepository;


import java.math.BigDecimal;
import java.util.*;

public class OrderOrchestratorTest extends TestCase {
    private String customerId;
    private Map<String, Integer> products;
    private OrderCreationUseCase orderCreationUseCase;
    private OrderSagaUseCase orderSagaUseCase;

    private OrderRepository orderRepository;
    private EventRepository eventRepository;
    private ProductRepository productRepository;
    private SagaOrchestrator<OrderSaga> sagaOrchestrator;
    private SagaRepository sagaRepository;

    public void setUp() throws Exception {
        this.productRepository = new ImProductRepository();
        this.eventRepository = new ImEventRepository();
        this.orderRepository = new ImOrderRepository();
        this.sagaRepository = new ImSagaRepository();

        this.orderCreationUseCase = new OrderCreationUseCaseImpl(
                this.orderRepository, this.eventRepository, this.productRepository
        );

        this.sagaOrchestrator = new OrderSagaOrchestrator(this.sagaRepository, this.eventRepository);
        this.orderSagaUseCase = new OrderSagaUseCaseImpl((OrderSagaOrchestrator) this.sagaOrchestrator, this.orderRepository, this.sagaRepository);

        this.customerId = "customer_1";
        this.products = createProductSales();

        this.initProductList(this.products);
    }

    private void initProductList(Map<String, Integer> products) {
        for (String productId : products.keySet()) {
            this.productRepository.save(new Product(new ProductId(productId), new Price(new BigDecimal(1000))));
        }
    }

    private Map<String, Integer> createProductSales() {
        Map<String, Integer> products = new HashMap<>();
        products.put("apple", 1);
        products.put("banana", 2);
        products.put("cherry", 3);

        return products;
    }

    public void testRepositoriesWork() throws Exception {
        Optional<Product> apple = this.productRepository.read(new ProductId("apple"));
        assertNotNull(apple.get());
    }

    public void testOrderCreatedSaga() throws Exception {
        createNewOrder();

        List<Order> orders = this.orderRepository.readAll();
        assertEquals(1, orders.size());
        System.out.println(orders.get(0).toString());

        List<DomainEvent<?>> events = this.eventRepository.readAll();
        assertEquals(1, events.size());
        System.out.println(events.get(0).toString());
    }

    public void testInitOrderSaga() {
        initOrderSaga();
        List<DomainEvent<?>> events = this.eventRepository.readAll();
        assertEquals(2, events.size());
        System.out.println(events);

        List<OrderSaga> orderSagaList = this.sagaRepository.readAll();
        assertEquals(1, orderSagaList.size());
        System.out.println(orderSagaList.get(0));
    }

    public void testInventoryReservationSuccess() throws InvalidSagaInputException {
        initOrderSaga();
        List<DomainEvent<?>> events = this.eventRepository.readAll();
        DomainEvent<InventoryReservationRequestedEventContent> inventoryRequest = (DomainEvent<InventoryReservationRequestedEventContent>) filterByType(events, new EventType("InventoryReservationRequest_Saga"));
        assertNotNull(inventoryRequest);

        // Messaging start...
        InventoryReservationUseCase inventoryReservationUseCase = new InventoryReservationUseCaseImpl();
        InventoryReservationInput inventoryReservationInput = new InventoryReservationInput(new SagaId(inventoryRequest.aggregateId().value()));
        DomainEvent<InventoryReservationProcessedEventContent> inventoryReservedEvent = inventoryReservationUseCase.requestInventoryReservation(inventoryReservationInput, true);
        InventoryReservationResultInput inventoryReservationResultInput = new InventoryReservationResultInput(inventoryReservedEvent.content().sagaId(), inventoryReservedEvent.content().isSuccess());
        // ~ Messaging end

        this.orderSagaUseCase.handleInventoryReservationResult(inventoryReservationResultInput);
    }

    public void testOrderCreationSuccess() throws InvalidSagaInputException {
        initOrderSaga();

        // Messaging start...
        List<DomainEvent<?>> events = this.eventRepository.readAll();
        DomainEvent<InventoryReservationRequestedEventContent> inventoryRequest = (DomainEvent<InventoryReservationRequestedEventContent>) filterByType(events, new EventType("InventoryReservationRequest_Saga"));
        assertNotNull(inventoryRequest);

        InventoryReservationUseCase inventoryReservationUseCase = new InventoryReservationUseCaseImpl();
        InventoryReservationInput inventoryReservationInput = new InventoryReservationInput(new SagaId(inventoryRequest.aggregateId().value()));
        DomainEvent<InventoryReservationProcessedEventContent> inventoryReservationProcessedEvent = inventoryReservationUseCase.requestInventoryReservation(inventoryReservationInput, true);
        InventoryReservationResultInput inventoryReservationResultInput = new InventoryReservationResultInput(inventoryReservationProcessedEvent.content().sagaId(), inventoryReservationProcessedEvent.content().isSuccess());
        // ~ Messaging end

        this.orderSagaUseCase.handleInventoryReservationResult(inventoryReservationResultInput);

        // Messaging start...
        events = this.eventRepository.readAll();
        DomainEvent<PaymentProcessRequestedEventContent> paymentRequest = (DomainEvent<PaymentProcessRequestedEventContent>) filterByType(events, new EventType("PaymentProcessRequest_Saga"));
        assertNotNull(paymentRequest);
        DomainEvent<PaymentProcessedEventContent> paymentProcessedEvent = this.mockPaymentProcessedEvent(paymentRequest);
        PaymentProcessResultInput paymentProcessResultInput = new PaymentProcessResultInput(paymentProcessedEvent.content().sagaId(), paymentProcessedEvent.content().isSuccess());
        // ~ Messaging end

        this.orderSagaUseCase.handlePaymentProcessResult(paymentProcessResultInput);

        // Messaging start...
        events = this.eventRepository.readAll();
        DomainEvent<InventoryReservationConfirmationRequestedEventContent> inventoryConfirmationRequest = (DomainEvent<InventoryReservationConfirmationRequestedEventContent>) filterByType(events, new EventType("InventoryReservationConfirmationRequest_Saga"));
        assertNotNull(inventoryConfirmationRequest);
        DomainEvent<InventoryReservationProcessedEventContent> inventoryReservationConfirmedEvent = this.mockInventoryReservationConfirmedEvent(inventoryConfirmationRequest);
        InventoryReservationConfirmationResultInput inventoryReservationConfirmationResultInput = new InventoryReservationConfirmationResultInput(inventoryReservationConfirmedEvent.content().sagaId(), inventoryReservationConfirmedEvent.content().isSuccess());
        // ~ Messaging end

        this.orderSagaUseCase.handleInventoryReservationConfirmationResult(inventoryReservationConfirmationResultInput);

        // Messaging start...
        events = this.eventRepository.readAll();
        DomainEvent<OrderApprovalRequestedEventContent> orderApprovalRequest = (DomainEvent<OrderApprovalRequestedEventContent>) filterByType(events, new EventType("OrderApprovalRequest_Saga"));
        assertNotNull(orderApprovalRequest);
        // ~ Messaging end

        // Approve Order
        this.orderCreationUseCase.approveOrder(new OrderApprovalInput(orderApprovalRequest.content().orderId()));

        Order approvedOrder = this.orderRepository.read(orderApprovalRequest.content().orderId()).orElseThrow(() -> new IllegalArgumentException("Failed to find approved order"));
        assertNotNull(approvedOrder);
    }

    private DomainEvent<InventoryReservationProcessedEventContent> mockInventoryReservationConfirmedEvent(DomainEvent<InventoryReservationConfirmationRequestedEventContent> inventoryConfirmationRequest) {
        return new DomainEvent<>(
             inventoryConfirmationRequest.aggregateId(),
                new EventType("Inventory Confirmed"),
                new IssuerId("Inventory Service"),
                new Date(),
                new InventoryReservationProcessedEventContent(inventoryConfirmationRequest.content().sagaId(), OperationType.CONFIRMATION, true)
        );
    }

    private DomainEvent<PaymentProcessedEventContent> mockPaymentProcessedEvent(DomainEvent<PaymentProcessRequestedEventContent> paymentRequest) {
        return new DomainEvent<>(
                paymentRequest.aggregateId(),
                new EventType("Inventory Reserved"),
                new IssuerId("Inventory Service"),
                new Date(),
                new PaymentProcessedEventContent(paymentRequest.content().sagaId(), true)
        );
    }

    public void testInventoryReservationFailure() {

    }

    private void createNewOrder() {
        OrderCreationInput orderCreationInput = new OrderCreationInput(this.customerId, this.products);
        this.orderCreationUseCase.createOrder(orderCreationInput);
    }

    private void initOrderSaga() {
        createNewOrder();
        DomainEvent<?> orderCreatedEvent = getOrderCreatedEvent();
        try {
            this.orderSagaUseCase.handleOrderCreationResult((DomainEvent<OrderCreatedEventContent>) orderCreatedEvent);
        } catch (InvalidSagaInputException e) {
            fail();
        }
    }

    private DomainEvent<?> getOrderCreatedEvent() {
        List<DomainEvent<?>> events = this.eventRepository.readAll();
        return events.get(0);
    }

    private DomainEvent<?> filterByType(List<DomainEvent<?>> events, EventType eventType) {
        for (DomainEvent event : events) {
            if (event.type().equals(eventType)) {
                return event;
            }
        }

        return null;
    }
}
