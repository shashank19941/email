package email;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailAuthSMTP {

	private static String SMTP_HOST_NAME = "164.100.14.95";
	private static final String SMTP_AUTH_USER = "adv-support@nic.in"; // user's email address
	private static final String SMTP_AUTH_PWD = "akaBaba@3402"; // Password
	Multipart multipart = new MimeMultipart();
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
	LocalDateTime now = LocalDateTime.now();
	LocalDateTime minusDay = LocalDateTime.now().minusDays(1);
	String subject = "Aadhaar Data Vault Transaction Details for " + dtf.format(minusDay);

	public void test1(String toEmail, BodyPart messageBodyPart) throws Exception {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.debug", "true");
		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth);
		System.out.println("mailSession:" + mailSession);
		Transport transport = mailSession.getTransport();
		System.out.println("transport:" + transport);
		// String msg = "Test message";
		MimeMessage message = new MimeMessage(mailSession);
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		message.setSubject(subject);
		message.setFrom(new InternetAddress("adv-support@nic.in")); // from address
		String[] listofIDS = {"esigngateway@nic.in","support-edmsd@nic.in","bhuvanesh.g@gov.in"};
		
		//String[] listofIDS = {"esigngateway@nic.in","officialjitendra151@gmail.com"};

		for (String cc : listofIDS) {
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
		}
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		transport.connect();
		System.out.println("message:" + message);
		System.out.println("Message:" + Message.RecipientType.TO);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.CC));
		// transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}