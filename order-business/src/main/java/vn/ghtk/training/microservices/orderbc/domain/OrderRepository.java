package vn.ghtk.training.microservices.orderbc.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> read(OrderId id);
    List<Order> readAll();
}
