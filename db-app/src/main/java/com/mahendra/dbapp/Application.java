package com.mahendra.dbapp;

import java.util.HashMap;
import java.util.List;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class Application {

	public static void main(String[] args) {
		DefaultCredentialsProvider provider = DefaultCredentialsProvider.create();
		SqsClient client = SqsClient.builder().credentialsProvider(provider).region(Region.AP_SOUTH_1).build();
		ReceiveMessageRequest request = ReceiveMessageRequest.builder().queueUrl("orders.fifo").build();

		List<Message> messages = client.receiveMessage(request).messages();

		if (messages.isEmpty()) {
			System.out.println("NO messages found, please try again ....");
		} else {
			System.out.println("Messages received " + messages.size());
			for (Message m : messages) {
				System.out.println(m.body());
				updateDB(m, provider);
			}
		}

	}

	static void updateDB(Message msg, DefaultCredentialsProvider provider) {
		DynamoDbClient client = DynamoDbClient.builder().credentialsProvider(provider).region(Region.AP_SOUTH_1)
				.build();
		String[] data = msg.body().split("; " );
		
		putItemInTable(client, data[1], data[0]);
		client.close();
	}

	public static void putItemInTable(DynamoDbClient ddb, String productname, String orderid) {

		HashMap<String, AttributeValue> itemValues = new HashMap<>();
		itemValues.put("orderid", AttributeValue.builder().n(orderid).build());
		itemValues.put("productname", AttributeValue.builder().s(productname).build());

		PutItemRequest request = PutItemRequest.builder().tableName("orders").item(itemValues).build();

		try {
			PutItemResponse response = ddb.putItem(request);
			System.out.println("Orders was successfully updated. The request id is "
					+ response.responseMetadata().requestId());

		} catch (ResourceNotFoundException e) {
			System.err.format("Error: The Amazon DynamoDB table \"orders\" can't be found.\n");
			System.err.println("Be sure that it exists and that you've typed its name correctly!");
			System.exit(1);
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}
