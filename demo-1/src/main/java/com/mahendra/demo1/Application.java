package com.mahendra.demo1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

public class Application {

	public static void main(String[] args) {

		DefaultCredentialsProvider provider = DefaultCredentialsProvider.create();

		DynamoDbClient client = DynamoDbClient.builder()
									.credentialsProvider(provider)
									.region(Region.AP_SOUTH_1)
									.build();
		
		System.out.println(client.serviceName());
		printRecords(client, "contacts", "101");
		
		client.close();
	}

	static void printRecords(DynamoDbClient client, String table, String contactId) {
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("contactid", AttributeValue.fromN(contactId));
		
		GetItemRequest req = GetItemRequest.builder().key(keyToGet).tableName(table).build();
		
		try {
			Map<String, AttributeValue > returnedValue = client.getItem(req).item();
			if (returnedValue != null) {
				Set<String> keys = returnedValue.keySet();
				System.out.println("Table attributes : ");
				for(String key : keys) {
					System.out.println(key+" = "+returnedValue.get(key));
				}
			}
		}catch(DynamoDbException ex) {
			System.out.println("Error connecting DynamoDB ... ");
			ex.printStackTrace();
			return;
		}
	}
}
