package com.resolute.smtp;

import static java.util.Objects.requireNonNull;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SmtpServiceImpl implements SmtpService {
    private static final Logger log = LoggerFactory.getLogger(SmtpServiceImpl.class);

    private final SmtpConfiguration config;

    static Builder builder() {
        return new Builder();
    }

    private SmtpServiceImpl(Builder builder) {
        this.config = builder.config;
    }

    @Override
    public void start() throws Exception {
        String transport = config.getTransport();
        transport = transport == null ? "smtp" : transport;
        String propPrefix = "mail.smtp";

        Properties systemProps = System.getProperties();
        systemProps.put("mail.transport.protocol", transport);


        setIfNotNull(systemProps, propPrefix + ".port", config.getPort());
        setIfNotNull(systemProps, propPrefix + ".auth", config.getUseAuth());
        setIfNotNull(systemProps, propPrefix+".starttls.enable", config.getStarttlsEnabled());
        setIfNotNull(systemProps, propPrefix+".starttls.required", config.getStarttlsRequired());

        Session.getDefaultInstance(systemProps);
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public void send(String to, String subject, String body) throws MailException {
        send(null, to, subject, body);

    }

    @Override
    public void send(String from, String to, String subject, String body) throws MailException {
        requireNonNull(to, "to cannot be null");
        requireNonNull(subject, "subject cannot be null");
        requireNonNull(body, "body cannot be null");
        if (from == null) {
            from = config.getSender();
        }

        Session session = Session.getDefaultInstance(System.getProperties());

        Transport transport = null;

        // Send the message.
        try {

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setContent(body, "text/plain");

            transport = session.getTransport();

            log.debug("Attempting to send an email through the SMTP interface...");

            // Connect using the SMTP username and password you specified above.
            if(config.getPassword() != null) {
                transport.connect(config.getHost(), config.getUsername(), config.getPassword());
            } else {
                transport.connect(config.getHost(), config.getUsername());
            }


            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            log.debug("Email sent!");
        } catch (Exception ex) {
            log.debug("The email was not sent.");
            log.debug("Error message: " + ex.getMessage());
            throw new MailException("A problem occurred while attempting to send an email through the SMTP interface", ex);
        } finally {
            // Close and terminate the connection.
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new MailException("A problem occurred while attempting to send an email through the SMTP interface", e);
                }
            }
        }

    }


    public static class Builder implements SmtpService.Builder {
        private SmtpConfiguration config;

        private Builder() {
        }

        @Override
        public Builder withConfig(SmtpConfiguration config) {
            this.config = requireNonNull(config, "config cannot be null");
            return this;
        }

        @Override
        public SmtpServiceImpl build() {
            requireNonNull(config, "config cannot be null");
            return new SmtpServiceImpl(this);
        }

    }


    private void setIfNotNull(Properties systemProps, String key, String value) {
        if(config.getPort() != null) {
            systemProps.put(key, value);
            System.out.println(key + "=" + value);
        }
    }


}
