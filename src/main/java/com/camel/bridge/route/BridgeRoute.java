package com.camel.bridge.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BridgeRoute extends RouteBuilder {

    @Value("${camel.destination.in.amq}")
    private String amqInEndpoint;

    @Value("${camel.destination.in.mq}")
    private String mqInEndpoint;

    @Value("${camel.destination.out.amq}")
    private String amqOutEndpoint;

    @Value("${camel.destination.out.mq}")
    private String mqOutEndpoint;

    @Override
    public void configure() throws InterruptedException {

        LOG.debug(amqInEndpoint);
        LOG.debug(mqInEndpoint);
        LOG.debug(amqOutEndpoint);
        LOG.debug(mqOutEndpoint);

        from(amqInEndpoint)
                .routeId("amq.to.mq.bridge")
                .log("============= Received from amq: ${body}")
                .log("============= Forwarding to mq")
                .to(mqOutEndpoint);

        from(mqInEndpoint)
                .routeId("mq.to.amq.bridge")
                .log("============= Received from mq: ${body}")
                .log("============= Forwarding to amq")
                .to(amqOutEndpoint);
    }
}
