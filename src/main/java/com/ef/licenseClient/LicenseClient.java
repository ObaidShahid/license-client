package com.ef.licenseClient;

import javax.jms.JMSException;
import java.util.Date;

public class LicenseClient {

    public static void main(String args[]) throws JMSException {

        String message = "{\n" +
                "  \"type\":\"USER_LOGGED_IN\",\n" +
                "  \"eventTime\":\"8379827892ye\",\n" +
                "  \"productId\":70304,\n" +
                "  \"object\":{\n" +
                "    \"objectId\":11002,\n" +
                "    \"objectType\":\"asad\"\n" +
                "  },\n" +
                "  \"modificationType\":\"REMOVED\"\n" +
                "}";
         LicenseClient licenseClient = new LicenseClient("http://127.0.0.1:8080","tcp://127.0.0.1:61616","newTopic","http");
//         licenseClient.publishEvent(message);
        Object res = licenseClient.getProductStatus(70304);
        System.out.println(res);
    }

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

    public void publishEvent(String message) throws JMSException {
        amqProducer.publish(message);
    }

}
