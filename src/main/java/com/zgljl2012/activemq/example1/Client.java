package com.zgljl2012.activemq.example1;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;

public class Client {
	
	public static void main(String[] args) throws InterruptedException {
		String username = "admin";
		String password = "admin";
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		// 创建生产者
		Producer producer;
		try {
			producer = new Producer(url, username, password);
			// 生产者产生一条消息
			producer.sendMessage("Hello World");
			// producer.close();
			// 创建消费者
			Consumer consumer = new Consumer(url, username, password);
			// 消费者读取一条消息
			Object msg = consumer.receive();
			
			// 输出消息内容
			System.out.println(msg);
			
			producer.close();
			consumer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
