package vn.ghtk.training.microservices.saga.domain.saga;

public class InvalidSagaInputException extends Exception {
    public InvalidSagaInputException(String message) {
        super(message);
    }
}
