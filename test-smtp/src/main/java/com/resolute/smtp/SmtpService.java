package com.resolute.smtp;

import com.resolute.lifecycle.Managed;

public interface SmtpService extends Managed {

    public static Builder builder() {
        return SmtpServiceImpl.builder();
    }

    public void send(String from, String to, String subject, String body) throws MailException;

    public void send(String to, String subject, String body) throws MailException;

    public static interface Builder {
        public Builder withConfig(SmtpConfiguration config);

        public SmtpService build();
    }

}
