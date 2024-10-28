package vn.ghtk.training.microservices.saga.domain.saga;

import vn.ghtk.training.microservices.commons.event.AggregateId;

import java.util.List;
import java.util.Optional;

public interface SagaRepository {
    <T extends Saga> Optional<T> read(SagaId sagaId);

    <T extends Saga> void save(T sagaData);

    <T extends Saga> T readBy(AggregateId aggregateId);

    <T extends Saga> List<T> readAll();
}
