package com.camel.bridge.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmqConsumer {

    @JmsListener(destination = "${amq.destination.consume}", containerFactory = "amqJmsListenerContainerFactory")
    public void handle(String payload) {

        LOG.info("============= Received from AMQ: " + payload);
    }
}
