package vn.ghtk.training.microservices.saga.domain.saga;

import vn.ghtk.training.microservices.commons.event.AggregateId;
import vn.ghtk.training.microservices.utilities.id.UUIDUtil;

import java.util.Date;
import java.util.Objects;

public abstract class Saga {
    protected SagaId id;
    protected AggregateId aggregateId;
    protected SagaState state;
    protected boolean isCompleted;
    protected Date createAt;
    protected Date updateAt;

    public Saga(AggregateId aggregateId) {
        this.id = new SagaId(UUIDUtil.genUUID());
        this.aggregateId = aggregateId;
        this.createAt = new Date();
        this.isCompleted = false;
    }

    public SagaOutput handleInput(SagaInput sagaInput) {
        SagaOutput sagaOutput = this.state.process(sagaInput);
        this.transit(sagaOutput.nextState());

        return sagaOutput;
    }

    protected void transit(SagaState sagaState) {
        this.state = sagaState;
    }

    public SagaId id() {
        return id;
    }

    public AggregateId aggregateId() {
        return aggregateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Saga saga = (Saga) o;
        return Objects.equals(id, saga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Saga{" +
                "id=" + id +
                ", aggregateId=" + aggregateId +
                ", state=" + state +
                ", isCompleted=" + isCompleted +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
