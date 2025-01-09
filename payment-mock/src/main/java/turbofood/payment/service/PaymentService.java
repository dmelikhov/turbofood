package turbofood.payment.service;

import java.util.concurrent.ThreadLocalRandom;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
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

    @Channel("payment-received")
    Emitter<PaymentReceivedEvent> paymentReceivedEventEmitter;

    @Channel("payment-failed")
    Emitter<PaymentFailedEvent> paymentFailedEventEmitter;

    @Incoming("order-created")
    public void onOrderCreatedEvent(byte[] b) throws Exception {
        OrderCreatedEvent event = new JsonObject(Buffer.buffer(b)).mapTo(OrderCreatedEvent.class);

        Thread.sleep(1000L);

        // randomly decide if the payment was successful
        if (ThreadLocalRandom.current().nextDouble() < 0.7) {
            LOG.info("Payment received for order " + event.getOrderId());
            paymentReceivedEventEmitter.send(new PaymentReceivedEvent(event.getOrderId()));
        } else {
            LOG.info("Payment failed for order " + event.getOrderId());
            paymentFailedEventEmitter.send(new PaymentFailedEvent(event.getOrderId()));
        }
    }

    @Incoming("order-rejected")
    public void onOrderRejectedEvent(byte[] b) throws Exception {
        OrderRejectedEvent event = new JsonObject(Buffer.buffer(b)).mapTo(OrderRejectedEvent.class);

        Thread.sleep(1000L);

        // refund the payment
        LOG.info("Refunding payment for order " + event.getOrderId());
    }

}
