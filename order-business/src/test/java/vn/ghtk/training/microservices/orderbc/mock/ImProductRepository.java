package vn.ghtk.training.microservices.orderbc.mock;

import vn.ghtk.training.microservices.orderbc.domain.Product;
import vn.ghtk.training.microservices.orderbc.domain.ProductId;
import vn.ghtk.training.microservices.orderbc.domain.ProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ImProductRepository implements ProductRepository {
    private final Map<ProductId, Product> productStore = new HashMap<>();

    public void save(Product product) {
        productStore.put(product.id(), product);
    }

    public Optional<Product> read(ProductId productId) {
        return Optional.ofNullable(productStore.get(productId));
    }
}
