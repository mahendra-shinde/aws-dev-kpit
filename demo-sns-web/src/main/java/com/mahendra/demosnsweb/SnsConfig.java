package com.mahendra.demosnsweb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class SnsConfig {

	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKeyId;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String accessSecret;
	
	@Primary
	@Bean
	public AmazonSNS snsClient() {
		System.out.println("Access key and secret "+ accessKeyId +" "+accessSecret);
		return AmazonSNSClientBuilder.standard().withRegion(region).withCredentials(
				new AWSStaticCredentialsProvider
					(new BasicAWSCredentials(accessKeyId, accessSecret)))
				.build();
	}
}
