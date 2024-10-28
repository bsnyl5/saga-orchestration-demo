package vn.ghtk.training.microservices.orderbc.port.inbound;

import vn.ghtk.training.microservices.orderbc.port.dto.OrderApprovalInput;
import vn.ghtk.training.microservices.orderbc.port.dto.OrderCreationInput;
import vn.ghtk.training.microservices.orderbc.port.dto.OrderRejectionInput;

public interface OrderCreationUseCase {
    void createOrder(OrderCreationInput input);
    void rejectOrder(OrderRejectionInput input);
    void approveOrder(OrderApprovalInput input);
}
