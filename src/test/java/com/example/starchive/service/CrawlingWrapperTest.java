package com.example.starchive.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CrawlingWrapperTest {

  @Autowired private CrawlingWrapper crawlingWrapper;

  @Value("${CHROME_DRIVER_PATH}")
  private String chromePath;

  @BeforeEach
  public void setUp() {
    if (chromePath != null) {
      System.setProperty("webdriver.chrome.driver", chromePath);
    }
  }

  @AfterEach
  public void tearDown() {
    // Tear down after each test, e.g., cleaning up the database, if necessary.
  }

  @Test
  public void testCrawlData() {
    // Act
    crawlingWrapper.crawlWrap();
    // Assert
    // Here, you would typically verify the results of the crawl.
    // For example, you could check if the expected data was inserted into the database.
    // Assertions would go here.
  }
}
