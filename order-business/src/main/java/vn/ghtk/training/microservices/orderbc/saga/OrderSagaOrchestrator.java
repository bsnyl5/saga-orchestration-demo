package vn.ghtk.training.microservices.orderbc.saga;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.commons.event.EventRepository;
import vn.ghtk.training.microservices.orderbc.saga.dto.OrderCreatedSagaInputContent;
import vn.ghtk.training.microservices.saga.domain.saga.SagaInput;
import vn.ghtk.training.microservices.saga.domain.saga.SagaOrchestrator;
import vn.ghtk.training.microservices.saga.domain.saga.SagaRepository;

public class OrderSagaOrchestrator extends SagaOrchestrator<OrderSaga> {
    public OrderSagaOrchestrator(SagaRepository sagaRepository, EventRepository eventRepository) {
        super(sagaRepository, eventRepository);
    }

    @Override
    protected OrderSaga createSagaData(SagaInput<?> sagaInput) {
        SagaInput<OrderCreatedSagaInputContent> orderCreatedSagaInput = (SagaInput<OrderCreatedSagaInputContent>) sagaInput;
        return new OrderSaga(new AggregateId(orderCreatedSagaInput.aggregateId().value()));
    }
}
