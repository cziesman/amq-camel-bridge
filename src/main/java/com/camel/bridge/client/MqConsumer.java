package com.camel.bridge.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqConsumer {

    @JmsListener(destination = "${mq.destination.consume}", containerFactory = "mqJmsListenerContainerFactory")
    public void handle(String payload) {

        LOG.info("============= Received from MQ: " + payload);
    }
}
