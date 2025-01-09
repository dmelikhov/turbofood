package turbofood.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables orderDeclarables() {
        return new Declarables(
                new TopicExchange("turbofood"),
                new Queue("order.created"),
                new Binding("order.created", DestinationType.QUEUE, "turbofood", "order.created", null),
                new Queue("order.confirmed"),
                new Binding("order.confirmed", DestinationType.QUEUE, "turbofood", "order.confirmed", null),
                new Queue("order.rejected"),
                new Binding("order.rejected", DestinationType.QUEUE, "turbofood", "order.rejected", null),
                new Queue("order.prepared"),
                new Binding("order.prepared", DestinationType.QUEUE, "turbofood", "order.prepared", null),
                new Queue("payment.received"),
                new Binding("payment.received", DestinationType.QUEUE, "turbofood", "payment.received", null),
                new Queue("payment.failed"),
                new Binding("payment.failed", DestinationType.QUEUE, "turbofood", "payment.failed", null),
                new Queue("courier.assigned"),
                new Binding("courier.assigned", DestinationType.QUEUE, "turbofood", "courier.assigned", null),
                new Queue("courier.arrived"),
                new Binding("courier.arrived", DestinationType.QUEUE, "turbofood", "courier.arrived", null),
                new Queue("notifications"),
                new Binding("notifications", DestinationType.QUEUE, "turbofood", "#", null)
        );
    }

}
