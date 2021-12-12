package cn.element.spring.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_topic_queue";

    /**
     * 1.配置交换机
     */
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * 2.配置队列
     */
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .build();
    }

    /**
     * 3.配置队列和和交换机的绑定关系
     */
    @Bean("bindQueueExchange")
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue,
                                     @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("boot.#")
                .noargs();
    }




}
