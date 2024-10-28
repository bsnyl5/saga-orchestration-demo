package vn.ghtk.training.microservices.orderbc.saga.event;

public class ProductReservation {
    private final String productId;
    private final int quantity;

    public ProductReservation(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


}
