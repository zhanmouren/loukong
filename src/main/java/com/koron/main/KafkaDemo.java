package com.koron.main;

import com.koron.inwlms.util.kafka.KafkaProperties;
import com.koron.inwlms.util.sendNote.SendNoteUtil;

public class KafkaDemo {
	
	 public static void main(String[] args)
	    {
	        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
	        producerThread.start();
//	        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
//	        consumerThread.start();
//		 String phone = "18502761868";
//			String content = "ceshi";
//			try {
//				SendNoteUtil.sendNote(phone,content);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    }

}
