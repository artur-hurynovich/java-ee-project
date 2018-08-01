package com.hurynovich.mus_site.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	private final static EmailSender INSTANCE = new EmailSender();
	
	private ResourceBundle mailBundle = BundleFactory.getInstance().getMailBundle();
	
	private Properties properties;
	
	private final String AUTH = "mail.smtp.auth";
	private final String TLS = "mail.smtp.starttls.enable";
	private final String HOST = "mail.smtp.host";
	private final String PORT = "mail.smtp.port";
	
	private EmailSender() {
		properties = new Properties();
		properties.put(AUTH, mailBundle.getString(AUTH));
		properties.put(TLS, mailBundle.getString(TLS));
		properties.put(HOST, mailBundle.getString(HOST));
		properties.put(PORT, mailBundle.getString(PORT));
	}
	
	public static EmailSender getInstance() {
		return INSTANCE;
	}
	
	public void send(String mailLang, String userName, String userEmail, String userPassword) {
		String fromEmail = mailBundle.getString("mail.from");
		String fromPassword = mailBundle.getString("mail.password");
		Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });

		String subject = mailBundle.getString("mail.subject." + mailLang);
		String text = mailBundle.getString("mail.text.greeting." + mailLang) + userName + ".\n" + 
			mailBundle.getString("mail.text.main." + mailLang) + " " + userPassword;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } 
	}
}
