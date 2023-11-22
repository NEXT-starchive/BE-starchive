package com.example.starchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling // enable scheduling
public class StarchiveApplication {
  @Value("${CHROME_DRIVER_PATH}")
  private String chromeDriverPath;
  public static void main(String[] args) {
    SpringApplication.run(StarchiveApplication.class, args);
  }

  @PostConstruct
  public void init() {
    System.setProperty(
        "webdriver.chrome.driver",
        chromeDriverPath); // added Chrome driver path to all crawling methods
  }
}
