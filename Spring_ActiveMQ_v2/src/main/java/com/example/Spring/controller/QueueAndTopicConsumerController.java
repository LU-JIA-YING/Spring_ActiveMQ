package com.example.Spring.controller;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueAndTopicConsumerController {

    private static final Logger logger = Logger.getLogger(QueueAndTopicConsumerController.class);

    @JmsListener(destination = "response.queue", containerFactory = "queueConnectionFactory")
    public void consumeMessageQueue(String message) {

        logger.info("Message received from activemq queue---\n" + message);
        System.out.println("=================================================\n");
//        System.out.println("Message received from activemq queue---\n" + message);
    }

//=================================================================================================

    @JmsListener(destination = "${mytopic}", containerFactory = "topicConnectionFactory")
    public void readActiveTopic(String msg) {

        logger.info("Message received from activemq topic\n" + msg);
        System.out.println("Topic1 : " + msg);
    }

    @JmsListener(destination = "${mytopic}", containerFactory = "topicConnectionFactory")
    public void readActiveTopic2(String msg) {

        logger.info("Message received from activemq topic\n" + msg);
        System.out.println("Topic2 : " + msg);
    }
}
