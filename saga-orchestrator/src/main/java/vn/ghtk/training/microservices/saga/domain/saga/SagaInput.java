package vn.ghtk.training.microservices.saga.domain.saga;

import vn.ghtk.training.microservices.commons.event.AggregateId;

public class SagaInput<T extends SagaInputContent> {
//    private SagaId sagaId;
    private AggregateId aggregateId;
    private T content;

    public SagaInput(AggregateId aggregateId, T content) {
        this.aggregateId = aggregateId;
        this.content = content;
    }

//    public SagaId sagaId() {
//        // TODO
//        return null;
//    }


    public AggregateId aggregateId() {
        return aggregateId;
    }

    public T content() {
        return content;
    }
}
