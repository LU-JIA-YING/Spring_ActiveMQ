package com.example.Spring;

import com.example.Spring.controller.TopicProducerTransferController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private TopicProducerTransferController topicProducerTransferController;
	@Test
	void contextLoads() throws Exception {
		topicProducerTransferController.sendMsg("你好");
	}

}
