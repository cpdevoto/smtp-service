package com.resolute.smtp;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = SmtpConfiguration.Builder.class)
public class SmtpConfiguration {

    private String username;
    private String password;
    private String host;
    private String port;
    private String transport;
    private String sender;
    private String starttlsRequired;
    private String starttlsEnabled;
    private String useAuth;
    
    @JsonCreator
    public static Builder builder() {
        return new Builder();
    }
    
    private SmtpConfiguration (Builder builder) {
      this.username = builder.username;
      this.password = builder.password;
      this.host = builder.host;
      this.port = builder.port;
      this.transport = builder.transport;
      this.sender = builder.sender;
      this.starttlsRequired = builder.starttlsRequired;
      this.starttlsEnabled = builder.starttlsEnabled;
      this.useAuth = builder.useAuth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getSender() {
        return sender;
    }

    public String getTransport() {
        return transport;
    }

    public String getStarttlsRequired() {
        return starttlsRequired;
    }

    public String getStarttlsEnabled() {
        return starttlsEnabled;
    }

    public String getUseAuth() {
        return useAuth;
    }
    
    @JsonPOJOBuilder
    public static class Builder {
      private String username;
      private String password;
      private String host;
      private String port;
      private String transport;
      private String sender;
      private String starttlsRequired;
      private String starttlsEnabled;
      private String useAuth;
      
      private Builder () {}

      public Builder withUsername(String username) {
        this.username = requireNonNull(username, "username cannot be null");
        return this;
      }

      public Builder withPassword(String password) {
        this.password = requireNonNull(password, "password cannot be null");
        return this;
      }

      public Builder withHost(String host) {
        this.host = requireNonNull(host, "host cannot be null");
        return this;
      }

      public Builder withPort(String port) {
        try {
          int intPort = Integer.parseInt(requireNonNull(port, "port cannot be null"));
          if (intPort <= 0) {
            throw new IllegalArgumentException("port must be a positive integer");
          }
          this.port = port;
        } catch (NumberFormatException ex) {
          throw new IllegalArgumentException("port must be a positive integer");
        }
        return this;
      }

      public Builder withTransport(String transport) {
        this.transport = requireNonNull(transport, "transport cannot be null");
        return this;
      }

      public Builder withSender(String sender) {
        this.sender = requireNonNull(sender, "sender cannot be null");
        return this;
      }

      public Builder withStarttlsRequired(String starttlsRequired) {
        this.starttlsRequired = requireNonNull(starttlsRequired, "starttlsRequired cannot be null");
        return this;
      }

      public Builder withStarttlsEnabled(String starttlsEnabled) {
        this.starttlsEnabled = requireNonNull(starttlsEnabled, "starttlsEnabled cannot be null");
        return this;
      }

      public Builder withUseAuth(String useAuth) {
        this.useAuth = requireNonNull(useAuth, "useAuth cannot be null");
        return this;
      }
      
      public SmtpConfiguration build () {
        requireNonNull(username, "username cannot be null");
        requireNonNull(host, "host cannot be null");
        requireNonNull(port, "port cannot be null");
        requireNonNull(sender, "sender cannot be null");
        requireNonNull(starttlsRequired, "starttlsRequired cannot be null");
        requireNonNull(starttlsEnabled, "starttlsEnabled cannot be null");
        requireNonNull(useAuth, "useAuth cannot be null");
        return new SmtpConfiguration(this);
      }
      
      
      
    }
}
