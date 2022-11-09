package com.mahendra.demosnsweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Controller
public class MessageSender {

	
	@Autowired private AmazonSNS client;
	
	@Value("${cloud.aws.sns.arn}")
	private String topic;
	
	@PostMapping("/send")
	public String sendMessage(@RequestParam String msg, Model view) {
		System.out.println("Sending message :"+msg);
		PublishRequest request = new PublishRequest();
		request.setMessage(msg); 
		request.setTopicArn(topic);
		PublishResult result = client.publish(request);
		
		view.addAttribute("result", "Message Sent");
		view.addAttribute("messageId",result.getMessageId());	
		return "index";
	}
}
