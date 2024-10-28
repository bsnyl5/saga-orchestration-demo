package vn.ghtk.training.microservices.orderbc.domain;

import java.math.BigDecimal;

public class Item {
    private Product product;
    private Quantity quantity;

    public Item(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product product() {
        return product;
    }

    public Quantity quantity() {
        return quantity;
    }

    public BigDecimal amount() {
        return this.product.calculateTotal(this.quantity).value();
    }
}
