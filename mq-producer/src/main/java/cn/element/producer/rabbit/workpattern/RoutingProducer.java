package cn.element.producer.rabbit.workpattern;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ的工作模式演示
 * 发布订阅模式
 */
public class RoutingProducer {

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

        /* 5.创建交换机
         * exchangeDeclare(String exchange,
         *                 BuiltinExchangeType type,
         *                 boolean durable,
         *                 boolean autoDelete,
         *                 boolean internal,
         *                 Map<String, Object> arguments)
         * 参数解释:
         *   1.exchange:            交换机名称
         *   2.type:                交换机类型
         *      DIRECT("direct"): 定向
         *      FANOUT("fanout"): 扇形(广播),发送消息到每一个与之绑定的队列
         *      TOPIC("topic"): 通配符的方式
         *      HEADERS("headers"): 参数匹配
         *   3.durable:             是否持久化
         *   4.autoDelete:          是否自动删除
         *   5.internal:            内部使用,一般都是false
         *   6.args:                参数列表
         */
        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);

        //6.创建队列
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);

        /* 7.绑定队列和交换机
         * queueBind(String queue, String exchange, String routingKey)
         * 参数解释:
         *   1.queue:           队列名称
         *   2.exchange:        交换机名称
         *   3.routingKey:      路由键,绑定规则,如果交换机的类型为fanout,routingKey设置为""
         */
        //队列1的绑定
        channel.queueBind(queue1Name, exchangeName, "error");

        //队列2的绑定
        channel.queueBind(queue2Name, exchangeName, "info");
        channel.queueBind(queue2Name, exchangeName, "error");
        channel.queueBind(queue2Name, exchangeName, "warning");

        //8.发送消息
        String body = "[ERROR] 日志信息: 张三调用了delete()方法...出错误了~~";
        channel.basicPublish(exchangeName, "error", null, body.getBytes());

        //9.释放资源
        channel.close();
        connection.close();

    }

}
