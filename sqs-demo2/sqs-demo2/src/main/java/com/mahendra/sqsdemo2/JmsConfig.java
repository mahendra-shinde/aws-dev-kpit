package com.mahendra.sqsdemo2;

import javax.jms.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@EnableJms
public class JmsConfig {

	
	SQSConnectionFactory factory =  new SQSConnectionFactory(
	        new ProviderConfiguration(),
	        SqsClient.builder().region(Region.AP_SOUTH_1)
			.credentialsProvider(ProfileCredentialsProvider.create()).build()
			);
	
	
		// Consume the messages
	   @Bean
	    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
	        DefaultJmsListenerContainerFactory factory =
	                new DefaultJmsListenerContainerFactory();
	        factory.setConnectionFactory(this.factory);
	        factory.setDestinationResolver(new DynamicDestinationResolver());
	        factory.setConcurrency("3-10");
	        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
	        return factory;
	    }

	   	// Sending / producing message
	   @Bean
	    public JmsTemplate defaultJmsTemplate() {
	        return new JmsTemplate(this.factory);
	    }
}
