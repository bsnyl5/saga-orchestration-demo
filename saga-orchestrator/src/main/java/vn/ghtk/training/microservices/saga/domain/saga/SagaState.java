package vn.ghtk.training.microservices.saga.domain.saga;

public abstract class SagaState<T extends Saga, U extends SagaInputContent> {
    protected T saga;
    protected StateName stateName;

    public SagaState(T saga) {
        this.saga = saga;
        initName();
    }

    public abstract SagaOutput process(SagaInput<U> sagaInput);

    protected abstract void initName();

    @Override
    public String toString() {
        return "SagaState{" +
                "stateName=" + stateName +
                '}';
    }
}
