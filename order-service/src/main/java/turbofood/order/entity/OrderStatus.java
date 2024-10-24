package turbofood.order.entity;

public enum OrderStatus {
    AWAITING_PAYMENT, /* PaymentReceivedEvent, PaymentFailedEvent */
    AWAITING_CONFIRMATION, /* OrderConfirmedEvent, OrderRejectedEvent */
    AWAITING_PREPARATION, /* OrderPreparedEvent */
    AWAITING_COURIER, /* CourierAssignedEvent */
    AWAITING_DELIVERY, /* CourierArrivedEvent */
    COMPLETED,
    CANCELED
}
