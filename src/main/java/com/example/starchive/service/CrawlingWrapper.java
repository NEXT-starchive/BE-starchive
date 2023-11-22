package com.example.starchive.service;

import com.example.starchive.entity.Twitter;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlingWrapper {
  @Autowired
  private GoodsService goodsService;
  @Autowired
  private TwitterService twitterService;
  @Autowired
  private YoutubeService youtubeService;

  public void crawlWrap(){
    goodsService.crawlData();
    System.out.print("\ngoods complete\n");
    twitterService.crawlData();
    System.out.print("\ntwitter complete\n");
    youtubeService.crawlData();
    System.out.print("\nyoutube complete\n");
  }


  @Scheduled(cron = "0 0 0 * * ?")
  public void runCrawl(){
    this.crawlWrap();
  }
}
