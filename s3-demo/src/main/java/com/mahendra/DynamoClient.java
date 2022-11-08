package com.mahendra;

import java.util.HashMap;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class DynamoClient {

	public static void main(String[] args) {
		String tableName = "contacts";
		
		AWSCredentials credentials = new BasicAWSCredentials("AKIA4MWES47HE4NSPTNX" ,"Y2mSTFDAesxmJM+iuZILhC0KFrtgZTad9bsLGrpx");
		
		AmazonDynamoDB client = AmazonDynamoDBClient.builder()
										.withCredentials(new AWSStaticCredentialsProvider(credentials))
										.withRegion(Regions.AP_SOUTH_1)
										.build();
		HashMap<String, AttributeValue> attValue = new HashMap<>();
		attValue.put("contactid", new AttributeValue().withN("15"));
		attValue.put("name", new AttributeValue().withS("Mahendra Shinde"));
		attValue.put("prog-lang", new AttributeValue().withS("Java"));
		
		client.putItem(tableName, attValue);
		System.out.println("Item created !");
		
	}

}
