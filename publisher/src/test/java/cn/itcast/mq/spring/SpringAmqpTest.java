package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MXK
 * @version 1.0
 * @description spring amqp
 * @date 2023/3/21 21:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() {
        String queueName = "simple.queue";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, spring amqp __ !";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }

    }

    @Test
    public void testSendFanoutExchange() {

        // 交换机名称
        String exchangeName = "root.fanout";

        // 消息
        String msg = "hello, fanoutExchange!";

        rabbitTemplate.convertAndSend(exchangeName, "", msg);
    }

    @Test
    public void testSendDirectExchange() {

        // 交换机名称
        String exchangeName = "root.direct";

        // 消息
        String msg = "hello, blue!";
        String msg2 = "hello, red!";

        rabbitTemplate.convertAndSend(exchangeName, "blue", msg);
        rabbitTemplate.convertAndSend(exchangeName, "red", msg2);

    }

    @Test
    public void testSendTopicExchange() {

        // 交换机名称
        String exchangeName = "root.topic";

        // 消息
        String msg = "hello, weather!";

        rabbitTemplate.convertAndSend(exchangeName, "china.weather", msg);

    }

    @Test
    public void testSendObjectQueue() {
        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "mxk");
        msg.put("age", 20);
        rabbitTemplate.convertAndSend("object.queue", msg);
    }
}

    