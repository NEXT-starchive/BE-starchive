package com.example.starchive.service;

import com.example.starchive.entity.Goods;
import com.example.starchive.entity.Twitter;
import com.example.starchive.entity.Youtube;
import com.example.starchive.repository.GoodsRepository;
import com.example.starchive.repository.TwitterRepository;
import com.example.starchive.repository.YoutubeRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@DataJpaTest
public class CrawlingWrapperTest {

  @Autowired private CrawlingWrapper crawlingWrapper;
  @Autowired private YoutubeRepository youtubeRepository;
  @Autowired private GoodsRepository goodsRepository;
  @Autowired private TwitterRepository twitterRepository;

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
    crawlingWrapper.crawlWrap(5);
    List<Youtube> youtubeList = youtubeRepository.findAll(PageRequest.of(0, 5)).getContent();
    List<Goods> goodsList = goodsRepository.findAll(PageRequest.of(0, 5)).getContent();
    List<Twitter> twitterList = twitterRepository.findAll(PageRequest.of(0, 5)).getContent();
    for(Youtube youtube : youtubeList)
      System.out.print(youtube.getTitle()+ "\n");

    System.out.print("\nyoutube done\n");
    for(Goods goods : goodsList)
      System.out.print(goods.getTitle()+ "\n");
    System.out.print("\ngoods done\n");
    for(Twitter twitter : twitterList)
      System.out.print(twitter.getContent()+ "\n");
    System.out.print("\ntwitter done\n");
    // Assert
    // Here, you would typically verify the results of the crawl.
    // For example, you could check if the expected data was inserted into the database.
    // Assertions would go here.
  }




}
