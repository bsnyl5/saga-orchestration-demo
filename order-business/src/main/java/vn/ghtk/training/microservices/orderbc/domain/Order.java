package vn.ghtk.training.microservices.orderbc.domain;

import vn.ghtk.training.microservices.commons.ddd.Entity;
import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventType;
import vn.ghtk.training.microservices.commons.event.IssuerId;
import vn.ghtk.training.microservices.orderbc.saga.event.ProductReservation;
import vn.ghtk.training.microservices.utilities.id.UUIDUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Entity<OrderId> {
    private OrderId id;
    private OrderStatus status;
    private CustomerId customerId;
    private Date createdAt;
    private Date updatedAt;
    private List<Item> items;

    public Order(CustomerId customerId) {
        this.id = new OrderId(UUIDUtil.genUUID());
        this.customerId = customerId;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
        this.createdAt = new Date();
    }

    public DomainEvent<OrderCreatedEventContent> generateEvent() {
        OrderCreatedEventContent eventContent = new OrderCreatedEventContent(
                this.id,
                this.status,
                this.customerId,
                this.items
        );
        return new DomainEvent<>(
                new AggregateId(this.id.value()),
                new EventType("OrderCreated"),
                new IssuerId(this.customerId.value()),
                this.createdAt,
                eventContent
        );
    }

    public void add(Product product, Quantity quantity) {
        this.items.add(new Item(product, quantity));
    }

    public OrderId id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

    public List<ProductReservation> createProductReservationRequest() {
        List<ProductReservation> productReservations = new ArrayList<>();
        for (Item item : this.items) {
            ProductReservation productReservation = new ProductReservation(item.product().id().value(), item.quantity().value());
            productReservations.add(productReservation);
        }
        return productReservations;
    }

    public PaymentProcessRequest createPaymentProcessRequest() {
        return new PaymentProcessRequest(this.customerId, this.getTotalAmount());
    }

    private BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Item item : this.items) {
            totalAmount.add(item.amount());
        }

        return totalAmount;
    }

    public void setApproved() {
        this.status = OrderStatus.APPROVED;
        this.updatedAt = new Date();
    }
}
