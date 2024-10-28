package vn.ghtk.training.microservices.inventorybc.usecase;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventType;
import vn.ghtk.training.microservices.commons.event.IssuerId;
import vn.ghtk.training.microservices.inventorybc.domain.InventoryReservationProcessedEventContent;
import vn.ghtk.training.microservices.inventorybc.domain.OperationType;
import vn.ghtk.training.microservices.inventorybc.port.dto.InventoryReservationConfirmationInput;
import vn.ghtk.training.microservices.inventorybc.port.dto.InventoryReservationInput;
import vn.ghtk.training.microservices.inventorybc.port.inbound.InventoryReservationUseCase;
import vn.ghtk.training.microservices.utilities.id.UUIDUtil;

import java.util.Date;

public class InventoryReservationUseCaseImpl implements InventoryReservationUseCase {
    @Override
    public DomainEvent<InventoryReservationProcessedEventContent> requestInventoryReservation(InventoryReservationInput input, boolean isSuccess) {
        return new DomainEvent<>(
                new AggregateId(UUIDUtil.genUUID()),
                new EventType("InventoryReservation"),
                new IssuerId("Inventory Service"),
                new Date(),
                new InventoryReservationProcessedEventContent(input.sagaId(), OperationType.RESERVATION, isSuccess)
        );
    }

    @Override
    public DomainEvent<InventoryReservationProcessedEventContent> confirmInventoryReservation(InventoryReservationConfirmationInput input) {
        return new DomainEvent<>(
                new AggregateId(UUIDUtil.genUUID()),
                new EventType("InventoryReservationConfirmation"),
                new IssuerId("Inventory Service"),
                new Date(),
                new InventoryReservationProcessedEventContent(input.sagaId(), OperationType.CONFIRMATION, true)
        );
    }
}
