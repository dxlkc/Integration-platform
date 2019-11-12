//package com.jit.NBJoin.config;
//
//import lombok.extern.log4j.Log4j2;
//
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Log4j2
//@Configuration
//public class RabbitmqConfig {
//
//    //创建交换机
//    @Bean
//    public DirectExchange JitExchange() {
//        log.info("RabbitMq-Exchange-Name : {}", "jit.exchange");
//        return new DirectExchange("jit.exchange", true, false);
//    }
//
//    //用来收发消息
//    @Bean
//    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {   //虽然报红  但是能找到这个bean 很奇怪
//        connectionFactory.createConnection();
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setExchange("jit.exchange");         //绑定交换机
//        rabbitTemplate.setRoutingKey("message.onenet");     //绑定routingkey
//
//        //ConfirmCallback  只确认消息是否正确到达 Exchange 中。
//        //ReturnCallback   消息没有正确到达队列时触发回调，如果正确到达队列不执行。
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
//            }
//        });
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
//            }
//        });
//        return rabbitTemplate;
//    }
//}
//
