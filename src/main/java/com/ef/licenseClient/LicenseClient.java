package com.ef.licenseClient;

import javax.jms.JMSException;

public class LicenseClient {

    AmqProducer amqProducer = new AmqProducer();
    public static LicenseClient licenseClientInstance;

    private String licenseManagerUrl;
    private String amqUrl;
    private String topicName;
    private String host;

    private LicenseClient(String licenseManagerUrl, String amqUrl, String topicName, String host) {
        this.licenseManagerUrl = licenseManagerUrl;
        this.amqUrl = amqUrl;
        this.topicName = topicName;
        this.host = host;
        amqProducer.ActiveMQConn(amqUrl,topicName);
    }

    public static LicenseClient getInstance(String licenseManagerUrl, String amqUrl, String topicName, String host){
        if(licenseClientInstance == null){
            licenseClientInstance = new LicenseClient(licenseManagerUrl,amqUrl,topicName,host);
        }
        return licenseClientInstance;
    }

    public Object getProductStatus(long productId) {
        System.out.println("Going to call license manager REST API against product ID : "+ productId);
        HttpClientService httpClientService = new HttpClientService();
        return httpClientService.getProductStatus(productId,licenseManagerUrl);
    }

    public void userLoggedIn(long productId, String modificationType, String eventTime, String obj) throws JMSException {
        amqProducer.publisher(productId,modificationType,eventTime,obj);
    }

    public void objectModified(long productId, String modificationType, String eventTime, String obj) throws JMSException {
        amqProducer.publisher(productId,modificationType,eventTime,obj);
    }

    public void activityFired(long productId, String modificationType, String eventTime, String obj) throws JMSException {
        amqProducer.publisher(productId,modificationType,eventTime,obj);
    }
}