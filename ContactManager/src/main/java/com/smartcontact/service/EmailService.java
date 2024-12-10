package com.smartcontact.service;

import java.util.Properties;
import org.springframework.stereotype.Service;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service

public class EmailService {
    /*this is responsible to send email    */
 public boolean sendEmail(String message, String subject, String to) {
	//setting email address from where emails will be sent
	boolean f = false;
	String from="ravirajpoot2204@gmail.com";
	
	// variable for gmail host
	
	String host="smtp.gmail.com";
	
	//get the system properties
	
	Properties properties = System.getProperties();
	System.out.println("Properties : "+properties);

	//setting important information to properties object
	
	
	
	//host set
	
	properties.put("mail.smtp.host", host);
	
	properties.put("mail.smtp.port", "465");

	properties.put("mail.smtp.ssl.enable", "true");
	
	properties.put("mail.smtp.auth", "true");
	
	//step1 : get the session object we can not get object directly we will use factory method
	
	Session session = Session.getDefaultInstance(properties, new Authenticator() {

	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {
		
		return new PasswordAuthentication("ravirajpoot2204@gmail.com","riemhxuihjwnubih");
	    }
	});
	 
	session.setDebug(true);
	
	//Step 2 : Compose the message [text,multi media]
	
	MimeMessage mimeMessage = new MimeMessage(session);

	try {
	    //setting sender email address
	    mimeMessage.setFrom(from);
	    //setting recipient to message 
	    mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	    
	    //adding subject to message 
	    mimeMessage.setSubject(subject);
	    
	    //adding text to message
	    
	//    mimeMessage.setText(message);
	  mimeMessage.setContent(message,"text/html");  
	    //send message using transport class
	    
	    Transport.send(mimeMessage);
	    
	    System.out.println("Sent Success...........************");
	    f=true;
	    
	    return f;
	    
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    return f;
	}
    }

}
