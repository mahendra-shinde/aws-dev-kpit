package com.mahendra;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;



public class MyHandler implements RequestHandler<Map<String,String> , String> {
	
	public String handleRequest(Map<String,String> event, Context context) {
		// Add log to AWS Console (Lambda Logs)
		LambdaLogger log = context.getLogger();
		String response =" Welcome to My Lambdas !";
		log.log("Processing the request....");
		log.log("Context: "+context.toString());
		log.log("Event :"+event.toString());
		return response;
	}
}
