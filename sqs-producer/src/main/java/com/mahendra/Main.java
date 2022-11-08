package com.mahendra;

import java.util.List;
import java.util.Scanner;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;


public class Main {

	private final static String QUEUE_URL="https://sqs.ap-south-1.amazonaws.com/851889547214/queue1.fifo";

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); //Initialize System Input using "Scanner"
		
		String cmd = "y";
		

		SqsClient client = SqsClient.builder().region(Region.AP_SOUTH_1)
				.credentialsProvider(ProfileCredentialsProvider.create()).build();
		
		while(cmd.equalsIgnoreCase("y")) {
			System.out.println("Enter the message to be delivered: ");
			String msg = sc.nextLine();
			sendMessage(client, msg);
			System.out.println("Do you want to send another message ?");
			cmd = sc.nextLine().substring(0, 1); // If user enters "yes" then extract "y" 
		}
	}
	
	private static void sendMessage(SqsClient client, String msg) {
		SendMessageRequest request = SendMessageRequest.builder()
										.queueUrl(QUEUE_URL)
										.messageBody(msg)
										.messageGroupId("group1")
										.messageDeduplicationId("d1")
										.build();
		client.sendMessage(request);
		System.out.println("Message sent !!");
	}

}
