package com.mahendra.sqsdemo2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationComponent {

	@Autowired
	protected JmsTemplate template;
	
	public void sendReply(String replayMessage) throws IOException{
		template.convertAndSend("replies",replayMessage);
	}
}
