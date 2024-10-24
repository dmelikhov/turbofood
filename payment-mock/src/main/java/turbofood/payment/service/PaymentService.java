package turbofood.payment.service;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import turbofood.payment.event.OrderCreatedEvent;
import turbofood.payment.event.OrderRejectedEvent;
import turbofood.payment.event.PaymentFailedEvent;
import turbofood.payment.event.PaymentReceivedEvent;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOG = Logger.getLogger(PaymentService.class);

    @Channel("order-events")
    Emitter<PaymentReceivedEvent> paymentReceivedEventEmitter;

    @Channel("order-events")
    Emitter<PaymentFailedEvent> paymentFailedEventEmitter;

    @Incoming("order-events")
    public void onOrderCreatedEvent(OrderCreatedEvent event) {
        // randomly decide if the payment was successful
        if (ThreadLocalRandom.current().nextDouble() < 0.7) {
            LOG.info("Payment received for order " + event.getOrderId());
            paymentReceivedEventEmitter.send(new PaymentReceivedEvent(event.getOrderId()));
        } else {
            LOG.info("Payment failed for order " + event.getOrderId());
            paymentFailedEventEmitter.send(new PaymentFailedEvent(event.getOrderId()));
        }
    }

    @Incoming("order-events")
    public void onOrderRejectedEvent(OrderRejectedEvent event) {
        // refund the payment
        LOG.info("Refunding payment for order " + event.getOrderId());
    }

}
