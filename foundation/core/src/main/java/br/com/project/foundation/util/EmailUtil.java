package br.com.project.foundation.util;

import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import br.com.project.commons.util.SetUtil;
import br.com.project.commons.util.StringUtil;

public class EmailUtil {
	
	public static String HOST_NAME = "171.100.10.132";
	public static Integer SMTP_PORT = 25;
	
	public static void send(String to, String from, String subject, String text) {
		SimpleEmail email = new SimpleEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(SMTP_PORT);
		try {
			email.setFrom(from);
			email.setSubject(subject != null ? subject : "");
			email.setMsg(text);
			email.addTo(to);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	public static void send(String from, List<String> toList, List<String> ccList, List<String> bccList, String subject, String path, String text) {
		
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(SMTP_PORT);
		
		try {
			email.setFrom(from);
			email.setSubject(subject != null ? subject : "");
			email.setMsg(text);
			
			if (SetUtil.nonEmpty(toList)) {
				for (String to : toList) {
					email.addTo(to);
				}
			}
			
			
			if (SetUtil.nonEmpty(ccList)) {
				for (String cc : ccList) {
					email.addCc(cc);
				}
			}		
			
			if (SetUtil.nonEmpty(bccList)) {
				for (String bcc : bccList) {
					email.addBcc(bcc);
				}
			}

			if (StringUtil.isNotEmpty(path)) {
				email.attach(anexo(path));
			}			
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}	
	
	public static void send(String from, List<String> toList, List<String> ccList, List<String> bccList, String subject, String path, String text, Boolean isHtml) {
		
		HtmlEmail email = new HtmlEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(SMTP_PORT);
		
		try {
			email.setFrom(from);
			email.setSubject(subject != null ? subject : "");
			email.setHtmlMsg(text);
			
			if (SetUtil.nonEmpty(toList)) {
				for (String to : toList) {
					email.addTo(to);
				}
			}
			
			
			if (SetUtil.nonEmpty(ccList)) {
				for (String cc : ccList) {
					email.addCc(cc);
				}
			}		
			
			if (SetUtil.nonEmpty(bccList)) {
				for (String bcc : bccList) {
					email.addBcc(bcc);
				}
			}

			if (StringUtil.isNotEmpty(path)) {
				email.attach(anexo(path));
			}			
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}	
	
	private static EmailAttachment anexo(String fileName) {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(fileName);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		
		return attachment;
	}
}
