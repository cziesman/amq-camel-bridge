package com.camel.bridge.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@Slf4j
public class AmqConfig {

    @Value("${amq.scheme}")
    private String brokerScheme;

    @Value("${amq.host}")
    private String brokerHost;

    @Value("${amq.port}")
    private String brokerPort;

    @Value("${amq.username}")
    private String brokerUsername;

    @Value("${amq.password}")
    private String brokerPassword;

    @Value("${amq.maxConnections}")
    private Integer brokerMaxConnections;

    @Bean
    public JmsTemplate amqJmsTemplate(CachingConnectionFactory amqCachingConnectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(amqCachingConnectionFactory);

        jmsTemplate.afterPropertiesSet();

        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory amqJmsListenerContainerFactory(CachingConnectionFactory amqCachingConnectionFactory) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(amqCachingConnectionFactory);
        factory.setCacheLevel(25);

        return factory;
    }

    @Bean(name = "amqCachingConnectionFactory")
    public CachingConnectionFactory amqCachingConnectionFactory(JmsConnectionFactory amqConnectionFactory) {

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(brokerMaxConnections);
        cachingConnectionFactory.setTargetConnectionFactory(amqConnectionFactory);
        cachingConnectionFactory.afterPropertiesSet();

        return cachingConnectionFactory;
    }

    @Bean
    public JmsConnectionFactory amqConnectionFactory() throws Exception {

        JmsConnectionFactory factory = new JmsConnectionFactory();
        factory.setRemoteURI(remoteUri());
        factory.setUsername(brokerUsername);
        factory.setPassword(brokerPassword);

        return factory;
    }

    private String remoteUri() {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(brokerScheme)
                .host(brokerHost)
                .port(brokerPort)
                .build();

        LOG.debug(uriComponents.toUriString());

        return uriComponents.toUriString();
    }
}
