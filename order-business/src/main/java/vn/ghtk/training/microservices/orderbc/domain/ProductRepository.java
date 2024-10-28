package vn.ghtk.training.microservices.orderbc.domain;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> read(ProductId productId);
    void save(Product product);
}
