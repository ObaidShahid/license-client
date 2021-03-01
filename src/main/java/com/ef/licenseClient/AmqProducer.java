package com.ef.licenseClient;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AmqProducer{
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
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void publish(String message) throws JMSException{

        System.out.println("Creating producer.");
        MessageProducer producer = session.createProducer(destination);

        // We will send an object message on ActiveMQ Topic'
        System.out.println("Constructing object to publish on ActiveMQ Topic.");

        ObjectMessage objectMessage = session.createObjectMessage(message);

        // Here we are sending our message!
//        System.out.println("Sending objects from publisher to ActiveMQ. ");
        producer.send(objectMessage);
        System.out.println("Object published on ActiveMQ successfully.");
    }
}
