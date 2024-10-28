package vn.ghtk.training.microservices.orderbc.mock;

import vn.ghtk.training.microservices.orderbc.domain.Order;
import vn.ghtk.training.microservices.orderbc.domain.OrderId;
import vn.ghtk.training.microservices.orderbc.domain.OrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImOrderRepository implements OrderRepository {
    private final Map<OrderId, Order> orderStore = new HashMap<>();

    @Override
    public void save(Order order) {
        orderStore.put(order.id(), order);
    }

    @Override
    public Optional<Order> read(OrderId id) {
        return Optional.ofNullable(orderStore.get(id));
    }

    @Override
    public List<Order> readAll() {
        return orderStore.values().stream().collect(Collectors.toList());
    }
}
