package com.koron.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koron.kafka.Bean.User;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	
	
	@Autowired
    private KafkaProducer kafkaProducer;
	@Autowired
    private KafkaConsumerDemo kafkacus;
	
	
	 @RequestMapping("/createMsg")
	 public void createMsg() {
		     User user=new User();
		     user.setAge(1);
		     user.setName("xiaozhan");
		     user.setPassword("123456");
	        kafkaProducer.sendUserMessage(user);
	  }
	 @RequestMapping("/createMsg1")
	 public void createMsg1() {
		     User user=new User();
		     user.setAge(1);
		     user.setName("xiaozhanq");
		     user.setPassword("123456q");
	        kafkaProducer.sendUserMessage(user);
	  }
	 
	 @RequestMapping("/readMsg")
	 public void readMsg() {
		 kafkacus.consume();
	  }
}
