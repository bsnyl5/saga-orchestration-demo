package vn.ghtk.training.microservices.saga.domain.saga;

import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventRepository;


public abstract class SagaOrchestrator<T extends Saga> {
    protected final SagaRepository sagaRepository;
    protected final EventRepository eventRepository;

    protected SagaOrchestrator(SagaRepository sagaRepository, EventRepository eventRepository) {
        this.sagaRepository = sagaRepository;
        this.eventRepository = eventRepository;
    }

    public void handle(SagaInput<?> sagaInput) {
        T sagaData = this.sagaRepository.readBy(sagaInput.aggregateId());

        if (sagaData == null) {
            sagaData = createSagaData(sagaInput);
        }

        SagaOutput sagaOutput = sagaData.handleInput(sagaInput);
        DomainEvent<?> domainEvent = sagaOutput.domainEvents();

        this.sagaRepository.save(sagaData);
        this.eventRepository.save(domainEvent);
    }

    protected abstract T createSagaData(SagaInput<?> sagaInput);
}
