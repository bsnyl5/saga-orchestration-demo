package vn.ghtk.training.microservices.orderbc.saga.event;

import vn.ghtk.training.microservices.commons.event.EventContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;

import java.util.ArrayList;
import java.util.List;

public class InventoryReservationRequestedEventContent implements EventContent {
    private SagaId sagaId;
    private List<ProductReservation> products;

    public InventoryReservationRequestedEventContent() {
        products = new ArrayList<>();
    }

    public void setSagaId(SagaId sagaId) {
        this.sagaId = sagaId;
    }

    public void addProduct(ProductReservation product) {
        products.add(product);
    }

    public SagaId sagaId() {
        return sagaId;
    }
}
