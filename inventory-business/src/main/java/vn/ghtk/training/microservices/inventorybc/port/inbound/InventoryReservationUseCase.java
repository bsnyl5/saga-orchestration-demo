package vn.ghtk.training.microservices.inventorybc.port.inbound;

import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.inventorybc.domain.InventoryReservationProcessedEventContent;
import vn.ghtk.training.microservices.inventorybc.port.dto.InventoryReservationConfirmationInput;
import vn.ghtk.training.microservices.inventorybc.port.dto.InventoryReservationInput;

public interface InventoryReservationUseCase {
    DomainEvent<InventoryReservationProcessedEventContent> requestInventoryReservation(InventoryReservationInput input, boolean needSuccess);
    DomainEvent<InventoryReservationProcessedEventContent> confirmInventoryReservation(InventoryReservationConfirmationInput input);
}
