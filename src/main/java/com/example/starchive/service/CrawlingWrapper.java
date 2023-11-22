package com.example.starchive.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CrawlingWrapper {
  @Autowired
  private  GoodsService goodsService;
  @Autowired
  private  TwitterService twitterService;
  @Autowired
  private  YoutubeService youtubeService;
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  // Constructor with @Autowired for services

  private void executeWithRetry(Runnable task, String taskName, int interval) {
    int attempts = 0;
    while (attempts < 3) {
      try {
        attempts++;
        task.run();
        return; // Successful execution, exit the method
      } catch (Exception e) {
        System.out.println("Error during " + taskName + " on attempt " + attempts + ": " + e.getMessage());
        if (attempts < 3) {
          try {
            TimeUnit.SECONDS.sleep(interval); // Wait for 1 minute before retrying
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Task interrupted", ie);
          }
        }
      }
    }
    System.out.println(taskName + " failed after 3 attempts");
  }

  private void crawlGoods() {
    System.out.println("Starting Goods crawl...");
    goodsService.crawlData();
    System.out.println("Goods crawl complete.");
  }

  private void crawlTwitter() {
    System.out.println("Starting Twitter crawl...");
    twitterService.crawlData();
    System.out.println("Twitter crawl complete.");
  }

  private void crawlYoutube() {
    System.out.println("Starting YouTube crawl...");
    youtubeService.crawlData();
    System.out.println("YouTube crawl complete.");
  }

  public void crawlWrap(int interval) {
    executeWithRetry(this::crawlGoods, "Goods Crawl", interval);
    executeWithRetry(this::crawlTwitter, "Twitter Crawl", interval);
    executeWithRetry(this::crawlYoutube, "YouTube Crawl",interval);
  }
  @Scheduled(cron = "0 0 0 * * ?")
  public void runCrawl() {
    crawlWrap(60);
  }
}
