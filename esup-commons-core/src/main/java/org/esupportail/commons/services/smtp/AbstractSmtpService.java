/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.smtp;

import java.io.File;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * An abstract implementation of SmtpService that does not support tests.
 */
@SuppressWarnings("serial")
public abstract class AbstractSmtpService implements SmtpService {

    @Override
	public boolean supportsTest() {
		return false;
	}

	@Override
	public void test() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void send(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody) {
		send(to, subject, htmlBody, textBody, (String) null);
	}

	@Override
	public void send(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files) {
		send(to, subject, htmlBody, textBody, files, null);
	}

	@Override
	public void sendtocc(
			final InternetAddress [] tos,
			final InternetAddress [] ccs,
			final InternetAddress [] bccs,
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files) {
		sendtocc(tos, ccs, bccs, subject, htmlBody, textBody, files, null);
	}

	@Override
	public void sendDoNotIntercept(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody) {
		sendDoNotIntercept(to, subject, htmlBody, textBody, (String) null);
	}

	@Override
	public void sendDoNotIntercept(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files) {
		sendDoNotIntercept(to, subject, htmlBody, textBody, files, null);
	}
}
