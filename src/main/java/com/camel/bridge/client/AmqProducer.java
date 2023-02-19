package com.camel.bridge.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmqProducer {

    @Autowired
    @Qualifier("amqJmsTemplate")
    public JmsTemplate jmsTemplate;

    @Value("${amq.destination.produce}")
    private String destination;

    public void sendMessage(String payload) {

        LOG.info("============= Sending to AMQ: " + payload);
        jmsTemplate.convertAndSend(destination, payload);
    }
}
