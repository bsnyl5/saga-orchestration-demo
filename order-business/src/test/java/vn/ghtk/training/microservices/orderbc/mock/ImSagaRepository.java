package vn.ghtk.training.microservices.orderbc.mock;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.saga.domain.saga.Saga;
import vn.ghtk.training.microservices.saga.domain.saga.SagaId;
import vn.ghtk.training.microservices.saga.domain.saga.SagaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImSagaRepository implements SagaRepository {
    private final Map<SagaId, Saga> sagaStore = new HashMap<>(); // Lưu trữ Sagas theo SagaId
    private final Map<AggregateId, SagaId> aggregateToSagaId = new HashMap<>(); // Lưu trữ mối quan hệ giữa AggregateId và SagaId

    @Override
    public <T extends Saga> Optional<T> read(SagaId sagaId) {
        return (Optional<T>) Optional.ofNullable(sagaStore.get(sagaId)); // Trả về Saga dựa trên SagaId
    }

    @Override
    public <T extends Saga> void save(T sagaData) {
        sagaStore.put(sagaData.id(), sagaData); // Lưu Saga theo SagaId
        aggregateToSagaId.put(sagaData.aggregateId(), sagaData.id()); // Cập nhật mối quan hệ giữa AggregateId và SagaId
    }

    @Override
    public <T extends Saga> T readBy(AggregateId aggregateId) {
        SagaId sagaId = aggregateToSagaId.get(aggregateId); // Lấy SagaId từ AggregateId
        return sagaId != null ? (T) sagaStore.get(sagaId) : null; // Trả về Saga hoặc null nếu không tìm thấy
    }

    @Override
    public <T extends Saga> List<T> readAll() {
        return (List<T>) sagaStore.values().stream().collect(Collectors.toList());
    }
}
