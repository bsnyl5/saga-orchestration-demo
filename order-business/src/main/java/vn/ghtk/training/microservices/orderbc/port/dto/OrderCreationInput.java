package vn.ghtk.training.microservices.orderbc.port.dto;

import vn.ghtk.training.microservices.orderbc.domain.Product;
import vn.ghtk.training.microservices.orderbc.domain.ProductId;
import vn.ghtk.training.microservices.orderbc.domain.Quantity;

import java.util.Iterator;
import java.util.Map;

public class OrderCreationInput {
    private final String customerId;
    private final Map<String, Integer> products;

    public OrderCreationInput(String customerId, Map<String, Integer> products) {
        this.customerId = customerId;
        this.products = products;
    }

    public String customerId() {
        return customerId;
    }

    public Map<String, Integer> products() {
        return this.products;
    }
}
