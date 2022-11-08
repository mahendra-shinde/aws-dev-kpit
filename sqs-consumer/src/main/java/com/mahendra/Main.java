package com.mahendra;

import java.util.List;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class Main {

	private final static String QUEUE_URL="https://sqs.ap-south-1.amazonaws.com/851889547214/queue1.fifo";
	
	
	public static void main(String[] args) {

		// Step 1 : Authenticate using "AWS Credentials" stored by AWS CLI
		// Default location is "/home/kpit/.aws/.credentials
		// The file is created when you used "aws configure" command

		SqsClient client = SqsClient.builder().region(Region.AP_SOUTH_1)
				.credentialsProvider(ProfileCredentialsProvider.create()).build();
		
		// Step 2: Infinite loop, listening for messages from queue.
		while(true) {
			System.out.println("Waiting for message ...");
			try {
			Thread.sleep(100);
			}catch(Exception ex) {ex.printStackTrace();}
			
			receiveMessages(client);
		}

		
	}
	
	private static void receiveMessages(SqsClient client) {
		try {
			// Create the "request" for messages
			ReceiveMessageRequest request = ReceiveMessageRequest
												.builder()
												.queueUrl(QUEUE_URL)
												.maxNumberOfMessages(5)
												.build();
			// Invoke "recieveMessage" method and get the response
			List<Message> messages = client.receiveMessage(request).messages();
			System.out.println("Received "+messages.size()+" messages.");
			for(Message m : messages) {
				//Display the message "Body"
				System.out.println(m.getValueForField("Body", String.class));
			}
		}catch(SqsException ex) {
			System.out.println("Message Exception: "+ex.getMessage());
			return;
		}
	}

}
