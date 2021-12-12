package cn.element.consumer.rabbit.workpattern;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PubSubConsumer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        /* 2.设置连接参数
         * IP地址默认值是127.0.0.1,
         * TCP协议通信默认端口是5672
         * 虚拟机默认值是 /
         * 用户名默认是 guest
         * 密码默认是 guest
         */
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/itcast");
        factory.setUsername("element");
        factory.setPassword("123456");

        //3.创建连接
        Connection connection = factory.newConnection();

        //4.创建Chanel
        Channel channel = connection.createChannel();

        String queue1Name = "test_fanout_queue1";

        /* 6.接受消息
         * basicConsume(String queue, boolean autoAck, Consumer callback)
         * 参数解释:
         *   1.queue:       队列名称
         *   2.autoAck:     是否自动确认
         *   3.callback:    回调对象
         */
        channel.basicConsume(queue1Name, true, new DefaultConsumer(channel) {
            /*
             * 这是一个回调函数,收到消息时会自动处理
             * @param consumerTag           标识
             * @param envelope              获取一些信息,交换机,路由key
             * @param properties            配置信息
             * @param body                  数据
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("consumerTag: " + consumerTag);
//                System.out.println("exchange: " + envelope.getExchange());
//                System.out.println("routing-key: " + envelope.getRoutingKey());
//                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));
                System.out.println("将日志信息打印到控制台.....");
            }
        });

        //不要关闭资源
    }
}
