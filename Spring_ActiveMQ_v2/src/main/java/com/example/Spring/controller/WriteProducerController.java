package com.example.Spring.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class WriteProducerController implements CommandLineRunner {

    @Autowired
    private JmsTemplate jmsTemplate;

    //  <WriteProducerController>/<QueueProducerTransferController> request.queue傳送訊息
    //  => <MQConfig> response.queue接收訊息
    //  => <ConsumerController> response.queue印出訊息
    @Override
    public void run(String... args) throws Exception {

        while (true) {

            System.out.println("Request : ");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            jmsTemplate.convertAndSend(new ActiveMQQueue("request.queue"), message);
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("結束輸入");
        System.exit(0);
    }
}
