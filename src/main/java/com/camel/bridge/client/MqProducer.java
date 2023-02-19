package com.camel.bridge.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqProducer {

    @Autowired
    @Qualifier("mqJmsTemplate")
    public JmsTemplate jmsTemplate;

    @Value("${mq.destination.produce}")
    private String destination;

    public void sendMessage(String payload) {

        LOG.info("============= Sending to MQ: " + payload);
        jmsTemplate.convertAndSend(destination, payload);
    }
}
