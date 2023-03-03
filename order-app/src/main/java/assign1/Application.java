package assign1;

import java.util.Scanner;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class Application {

	public static void main(String[] args) {
		DefaultCredentialsProvider provider = DefaultCredentialsProvider.create();
		SqsClient client = SqsClient.builder().credentialsProvider(provider).region(Region.AP_SOUTH_1).build();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter order id: " );
		String orderId = sc.nextLine();
		
		System.out.println("Enter product name: ");
		String productname = sc.nextLine();
		
		SendMessageRequest request = SendMessageRequest.builder().queueUrl("orders.fifo")
					.messageBody(orderId+"; "+productname)
					.messageDeduplicationId("orderid").messageGroupId("orderid").build();
		client.sendMessage(request);
		
		System.out.println("Message sent !");
	}

}
