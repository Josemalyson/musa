package com.br.musa.util;

import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@SuppressWarnings("unused")
public final class Email {
	public static boolean enviaEmail(String email, String assunto,String mensagem, String host, String de, String senha) throws MessagingException {
		return enviaEmail(email, assunto, mensagem, host, de, senha, null);
	}

	public static boolean enviaEmail(String email, String assunto, String mensagem, String host, String de, String senha, String porta)
			throws MessagingException {
		String deFinal = de;
		String senhaFinal = senha;
		Properties p = new Properties();
		p.put("mail.smtp.host", host);
		if (porta != null) {
			p.put("mail.smtp.port", porta);
		}
		p.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(p);
		MimeMessage msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(de));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		msg.setSentDate(Calendar.getInstance().getTime());

		msg.setSubject(assunto);
		msg.setContent(mensagem, "text/html");

		Transport.send(msg);

		return true;
	}

	public static boolean isValido(String email) {
		Pattern pattern = Pattern
				.compile(
						"([a-zA-Z0-9_\\-\\.]+)@((\\[a-z]{1,3}\\.[a-z]{1,3}\\.[a-z]{1,3}\\.)|(([a-zA-Z\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)",
						8);

		Matcher m = pattern.matcher(email);
		return m.matches();
	}
}