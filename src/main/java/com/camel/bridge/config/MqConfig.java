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
public class MqConfig {

    @Value("${mq.scheme}")
    private String brokerScheme;

    @Value("${mq.host}")
    private String brokerHost;

    @Value("${mq.port}")
    private String brokerPort;

    @Value("${mq.username}")
    private String brokerUsername;

    @Value("${mq.password}")
    private String brokerPassword;

    @Value("${mq.maxConnections}")
    private Integer brokerMaxConnections;

    @Bean
    public JmsTemplate mqJmsTemplate(CachingConnectionFactory mqCachingConnectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(mqCachingConnectionFactory);

        jmsTemplate.afterPropertiesSet();

        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory mqJmsListenerContainerFactory(CachingConnectionFactory mqCachingConnectionFactory) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(mqCachingConnectionFactory);
        factory.setCacheLevel(25);

        return factory;
    }

    @Bean
    public CachingConnectionFactory mqCachingConnectionFactory(JmsConnectionFactory mqConnectionFactory) {

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(brokerMaxConnections);
        cachingConnectionFactory.setTargetConnectionFactory(mqConnectionFactory);
        cachingConnectionFactory.afterPropertiesSet();

        return cachingConnectionFactory;
    }

    @Bean
    public JmsConnectionFactory mqConnectionFactory() throws Exception {

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
