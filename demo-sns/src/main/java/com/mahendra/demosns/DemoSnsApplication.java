package com.mahendra.demosns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.mahendra.demosns")
public class DemoSnsApplication implements CommandLineRunner{

	@Autowired private AmazonSNS client;
	@Value("${cloud.aws.sns.arn}") private String topic;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoSnsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String message = "Hello from Spring boot";
		PublishRequest request = new PublishRequest();
		request.setMessage(message); 
		request.setTopicArn(topic);
		
		client.publish(request);
		
	}

}
