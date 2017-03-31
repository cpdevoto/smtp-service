package com.resolute.smtp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class TestSmtpConfiguration {

  @Test
  public void test_json_deserialization () throws IOException {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    SmtpConfiguration config;
    try (FileInputStream in = new FileInputStream("service-config.yml.example")) {
      config = mapper.readValue(in, SmtpConfiguration.class);
    }
    assertNotNull(config);
    assertThat(config.getUsername(), equalTo("username"));
    assertThat(config.getPassword(), equalTo("password"));
    assertThat(config.getHost(), equalTo("email-smtp.us-east-1.amazonaws.com"));
    assertThat(config.getPort(), equalTo("25"));
    assertThat(config.getSender(), equalTo("notifications@resolutebi.com"));
    assertThat(config.getStarttlsEnabled(), equalTo("true"));
    assertThat(config.getStarttlsRequired(), equalTo("true"));
    assertThat(config.getTransport(), equalTo("smtps"));
    assertThat(config.getUseAuth(), equalTo("true"));

  }
}
