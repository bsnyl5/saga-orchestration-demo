package vn.ghtk.training.microservices.saga.domain.saga;

import vn.ghtk.training.microservices.commons.event.DomainEvent;
import vn.ghtk.training.microservices.commons.event.EventContent;

import java.util.List;

public class SagaOutput<T extends Saga, U extends SagaInputContent, V extends EventContent> {
    protected SagaInput<?> fromInput;
    protected Saga sagaData;
    protected DomainEvent<V> domainEvent;
    protected SagaState<T, U> nextState;

    public SagaOutput(SagaInput<?> input, Saga sagaData, DomainEvent<V> domainEvent, SagaState<T, U> nextState) {
        this.fromInput = input;
        this.sagaData = sagaData;
        this.domainEvent = domainEvent;
        this.nextState = nextState;
    }

    public DomainEvent<V> domainEvents() {
        return domainEvent;
    }

    public SagaState<?, ?> nextState() {
        return nextState;
    }
}
