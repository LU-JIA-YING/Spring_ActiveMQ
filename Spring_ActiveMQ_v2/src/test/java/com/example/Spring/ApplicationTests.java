package com.example.Spring;

import com.example.Spring.controller.TopicProducerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private TopicProducerController topicProducerController;
	@Test
	void contextLoads() throws Exception {
		topicProducerController.sendMsg("你好");
	}

}
