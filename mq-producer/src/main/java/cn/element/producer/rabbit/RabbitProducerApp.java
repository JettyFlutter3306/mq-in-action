package cn.element.producer.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者连接到RabbitMQ
 */
public class RabbitProducerApp {

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

        /* 5.创建队列Queue
         * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         * 参数解释:
         *  1.queue:         队列名称
         *  2.durable:       是否持久化,当mq重启之后还在
         *  3.exclusive:     是否独占,只能有一个消费者监听这个队列,当Connection关闭时,是否删除队列
         *  4.autoDelete:    是否自动删除,当没有Consumer时,自动删除掉
         *  5.args:          参数
         *
         * 如果没有当前名称的队列,那么就会创建这个队列,有则不会创建
         */
        channel.queueDeclare("hello_world", true, false, false, null);

        /* 6.发送消息
         * basicPublish(String exchange, String routingKey, boolean mandatory, BasicProperties props, byte[] body)
         * 参数解释:
         *  1.exchange:         交换机的名称,简单模式下,交换机会使用默认的 ""
         *  2.routingKey:       路由名称
         *  3.props:            配置信息
         *  4.body:             发送的消息数据
         */
        String body = "Hello rabbitmq~~~~~~";

        channel.basicPublish("", "hello_world", null, body.getBytes());

        //7.释放资源
//        channel.close();
//        connection.close();
    }
}
