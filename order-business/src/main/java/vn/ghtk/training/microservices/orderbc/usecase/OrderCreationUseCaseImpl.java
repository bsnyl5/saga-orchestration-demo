package vn.ghtk.training.microservices.orderbc.usecase;

import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventRepository;
import vn.ghtk.training.microservices.orderbc.domain.*;
import vn.ghtk.training.microservices.orderbc.port.dto.OrderApprovalInput;
import vn.ghtk.training.microservices.orderbc.port.dto.OrderCreationInput;
import vn.ghtk.training.microservices.orderbc.port.dto.OrderRejectionInput;
import vn.ghtk.training.microservices.orderbc.port.inbound.OrderCreationUseCase;

import java.util.Optional;

public class OrderCreationUseCaseImpl implements OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;

    public OrderCreationUseCaseImpl(OrderRepository orderRepository, EventRepository eventRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createOrder(OrderCreationInput input) {
        Order order = createNewOrder(input);
        DomainEvent<OrderCreatedEventContent> orderCreatedEvent = order.generateEvent();

        this.orderRepository.save(order);
        this.eventRepository.save(orderCreatedEvent);
    }

    @Override
    public void approveOrder(OrderApprovalInput input) {
        Order order = this.orderRepository.read(input.orderId()).orElseThrow(() -> new IllegalArgumentException("Order not found."));
        order.setApproved();

        this.orderRepository.save(order);
    }

    @Override
    public void rejectOrder(OrderRejectionInput input) {
        // TODO
    }

    private Order createNewOrder(OrderCreationInput input) {
        Order order = new Order(new CustomerId(input.customerId()));
        input.products().forEach((key, value) -> {
            Product product = productRepository.read(new ProductId(key)).orElseThrow(() -> new IllegalArgumentException("Product not found."));
            order.add(product, new Quantity(value));
        });

        return order;
    }
}
