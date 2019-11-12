package com.jit.PushService.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class RabbitConfig {

//    @Autowired
//    private CachingConnectionFactory connectionFactory;
//
//    @Autowired
//    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
//
//    /**
//     * 单一消费者
//     * @return
//     */
//    @Bean(name = "singleListenerContainer")
//    public SimpleRabbitListenerContainerFactory listenerContainer(){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setConcurrentConsumers(1);
//        factory.setMaxConcurrentConsumers(1);
//        factory.setPrefetchCount(1);
//        factory.setTxSize(1);
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//
//        return factory;
//    }

//    /**
//     * 多个消费者
//     * @return
//     */
//    @Bean(name = "multiListenerContainer")
//    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factoryConfigurer.configure(factory,connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
//        return factory;
//    }

//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames("oneNet.queue");              // 监听的队列
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);        // 手动确认
//        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {      //消息处理
//            System.out.println("====接收到消息=====");
//            System.out.println(new String(message.getBody()));
//            for (Object key : message.getMessageProperties().getHeaders().keySet()) {
//                Object value = message.getMessageProperties().getHeaders().get(key);
//                System.out.println("Key = " + key + ", Value = " + value);
//            }
//            if(message.getMessageProperties().getHeaders().get("error") == null){
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//                System.out.println("消息已经确认");
//            }else {
//                //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
//                System.out.println("消息拒绝");
//            }
//        });
//        return container;
//    }

    @Bean
    public DirectExchange JitExchange(){
        log.info("RabbitMq-Exchange-Name : {}","jit.exchange");
        return new DirectExchange("jit.exchange",true,false);
    }

    @Bean
    public Queue OneNetQueue(){
        log.info("RabbitMq-Queue-Name : {}","oneNet.queue");
        return new Queue("oneNet.queue",true);
    }

    @Bean
    public Binding OneNetBinding(){
        log.info("RabbitMq-RoutingKey-Name : {}","message.onenet");
        return BindingBuilder.bind(OneNetQueue()).to(JitExchange()).with("message.onenet");
    }
}
