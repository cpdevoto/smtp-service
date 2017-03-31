package com.resolute.smtp;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class TestSmtpService {

  private SmtpConfiguration config;
  
  @Before
  public void setup () throws IOException {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    try (FileInputStream in = new FileInputStream("service-config.yml")) {
      config = mapper.readValue(in, SmtpConfiguration.class);
    }
  }

  @Test
  public void test_send () throws Exception {
    SmtpService service = SmtpServiceImpl.builder()
        .withConfig(config)
        .build();
    service.start();
    service.send("cdevoto@maddogtechnology.com", "Testing SMTP Service", "It works!");
    service.stop();

  }

}
