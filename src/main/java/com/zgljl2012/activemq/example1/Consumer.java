package com.zgljl2012.activemq.example1;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费者
 * @author zgljl2012
 *
 */
public class Consumer {
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 连接地址，host:port，如localhost:8161
	 */
	private String url;
	
	/**
	 * 队列名
	 */
	private static final String QUEUE_NAME = "HELLO";
	
	//连接工厂
    private ConnectionFactory connectionFactory;
    
    //会话 接受或者发送消息的线程
    private Session session;
    
    //消息的目的地
    private Destination destination;
    
    //消息消费者
    private MessageConsumer messageConsumer;
    
    //与MQ的连接
    private Connection connection = null;
    
    public Consumer(String url, String username, String password) throws JMSException {
		this.url = url;
    	this.username = username;
    	this.password = password;
    	this.init();
	}
	
	/**
	 * 初始化方法
	 * @throws JMSException 
	 */
	private void init() throws JMSException {
		// 实例化工厂
    	connectionFactory = new ActiveMQConnectionFactory(
    			username, password, url);
    	// 获取连接
    	connection = connectionFactory.createConnection();
    	// 启动
    	connection.start();
    	// 获取session
    	session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
    	// 创建指定队列名的队列
    	destination = session.createQueue(Consumer.QUEUE_NAME);
    	// 创建消息消费者
    	messageConsumer = session.createConsumer(destination);
	}
	
	/**
	 * 接收消息
	 * @throws JMSException 
	 */
	public String receive() throws JMSException {
		TextMessage msg = (TextMessage) messageConsumer.receive(5000);
		if(msg == null) {
			return null;
		}
		return msg.getText();
	}
	
	public void close() throws JMSException {
		session.close();
		connection.close();
	}

}
