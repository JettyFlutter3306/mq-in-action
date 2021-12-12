package cn.element.spring;

import cn.element.spring.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend() {
        String body = "rabbit boot hello~~~";

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "boot.haha", body);
    }
}
