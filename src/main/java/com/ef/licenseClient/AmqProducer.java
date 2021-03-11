package com.ef.licenseClient;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;


public class AmqProducer{

    Logger logger = LoggerFactory.getLogger(AmqProducer.class);
    static ConnectionFactory connectionFactory;
    static Connection connection;
    static Session session;
    static Destination destination;

    public void ActiveMQConn(String url, String topicName) {
        try{
            connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = session.createTopic(topicName);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String message) throws JMSException{

        logger.info("Creating producer.");
        MessageProducer producer = session.createProducer(destination);

        // We will send an object message on ActiveMQ Topic'
        logger.info("Constructing object to publish on ActiveMQ Topic.");

        ObjectMessage objectMessage = session.createObjectMessage(message);

        // Here we are sending our message!
//        System.out.println("Sending objects from publisher to ActiveMQ. ");
        producer.send(objectMessage);
        logger.info("Object published on ActiveMQ successfully.");
    }
}
