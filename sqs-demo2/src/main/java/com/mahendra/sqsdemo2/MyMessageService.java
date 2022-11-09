package com.mahendra.sqsdemo2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MyMessageService {

	private Logger log = Logger.getLogger("MyMessageService");
	
	@Autowired
	private NotificationComponent notification;
	
	@JmsListener(destination = "my")
    public void receiveMessage(String message) throws JMSException {
        log.info("Message received");
        try {
            System.out.println("Message received: "+message);
            notification.sendReply("Your request "+message+ ", is done !");
        } catch (IOException ex) {
            log.log(Level.SEVERE,"Encountered error while parsing message.",ex);
            throw new JMSException("Encountered error while parsing message.");
        }
    }
}
