package vn.ghtk.training.microservices.orderbc.domain;

import java.util.Objects;

public class Product {
    private final ProductId productId;
    private final Price unitPrice;

    public Product(ProductId productId, Price unitPrice) {
        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    public Price calculateTotal(Quantity quantity) {
        return unitPrice.multiple(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }

    public ProductId id() {
        return this.productId;
    }
}
