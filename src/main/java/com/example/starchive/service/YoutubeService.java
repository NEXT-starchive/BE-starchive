package com.example.starchive.service;

import com.example.starchive.dto.TwitterResDto;
import com.example.starchive.dto.YoutubeResDto;
import com.example.starchive.dto.crawlingdto.YoutubeCrawlingDto;
import com.example.starchive.entity.Youtube;
import com.example.starchive.repository.YoutubeRepository;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.openqa.selenium.JavascriptExecutor;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.LocalTime;
import java.util.List;
import java.time.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
@RequiredArgsConstructor
public class YoutubeService implements CrawlingData {

  private final YoutubeRepository youtubeRepository;

  @Override
  public Page<YoutubeResDto> getData(Pageable pageable) {
    Page<Youtube> goodsList = youtubeRepository.findAll(pageable);
    Page<YoutubeResDto> goodsDtoList = goodsList.map(tube -> new YoutubeResDto(tube));
    return goodsDtoList;
  }

  public void crawlData() {

    //    String youtubePath = "https://www.youtube.com/@BTS/videos";
    //
    //    Document doc = Jsoup.connect(youtubePath).get();
    //
    //    Element outerContainer = doc.selectFirst("#contents");
    //    Elements innerRow;
    //    try {
    //      innerRow = outerContainer.select(".ytd-rich-grid-renderer");
    //    } catch (NullPointerException nullPointerException) {
    //      System.out.print("no inner row");
    //      return;
    //    }
    //    for (Element element : innerRow) {
    //
    //    }

    String chromeDriverPath = "C:/Users/Liam/Downloads/chromedriver_win64/chromedriver.exe";
    System.setProperty("webdriver.chrome.driver", chromeDriverPath);

    // define array list of youtube instance
    List<YoutubeCrawlingDto> youtubeInstances;
    ChromeDriver driver = new ChromeDriver(CrawlUtils.makeConfig());
    driver.get("https://www.youtube.com/@BTS/videos");
    CrawlUtils.stall();

    try {
      new WebDriverWait(driver, Duration.ofSeconds(15))
          .until(
              webDriver ->
                  ((JavascriptExecutor) webDriver)
                      .executeScript("return document.readyState")
                      .equals("complete"));

      // define wait

      String stackSet =
          "/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse/ytd-two-column-browse-results-renderer/div[1]/ytd-rich-grid-renderer/div[6]/ytd-rich-grid-row";
      List<WebElement> contentStacks = driver.findElements(By.xpath(stackSet));
      // iterate over stacks and find each div
      youtubeInstances = crawlYoutube(driver, contentStacks);
    } finally {
      driver.close();
    }

    for (int i = 0; i < youtubeInstances.size(); i++) {
      YoutubeCrawlingDto youtubeInstance = youtubeInstances.get(i);
      System.out.print("Index " + i + ": " + youtubeInstance);
      youtubeInstance.toEntity();
    }
    System.out.print("\ndone\n");
  }

  private LocalDateTime calculateUploadHour(String timeAgo) {
    int hours = Integer.parseInt(timeAgo.split(" ")[0]);
    ZoneId zoneId = ZoneId.of("Asia/Seoul"); // Korea Standard Time
    LocalDateTime time = LocalDateTime.now(zoneId).minusHours(hours);
    return time;
  }

  private List<YoutubeCrawlingDto> crawlYoutube(WebDriver driver, List<WebElement> contentStacks) {
    List<YoutubeCrawlingDto> youtubeInstances = new ArrayList<>();
    for (WebElement stack : contentStacks) {

      List<WebElement> elements =
          stack.findElements(By.xpath("./div/ytd-rich-item-renderer"));

      // get divs inside the stacks
      for (WebElement element : elements) {
        String metaDataXpath =
            "./div/ytd-rich-grid-media/div[1]/div[3]/div[1]/ytd-video-meta-block/div[1]/div[2]";
        String time = element.findElement(By.xpath(metaDataXpath + "/span[2]")).getText();
        // only today
        if (time.contains("days")) {
          System.out.print("today's post ended\n");
          return youtubeInstances;
        }
        LocalDateTime uploadTime = calculateUploadHour(time);

        String views = element.findElement(By.xpath(metaDataXpath + "/span[1]")).getText();

        String titleXpath = "./div/ytd-rich-grid-media/div[1]/div[3]/div[1]/h3";
        String title = element.findElement(By.xpath(titleXpath)).getText();

        String imgSrc = element.findElement(By.tagName("img")).getAttribute("src");
///div/ytd-rich-grid-media/div[1]/div[1]/ytd-thumbnail/a
        String urlXpath = "./div/ytd-rich-grid-media/div[1]/div[1]/ytd-thumbnail/a";
        String url = element.findElement(By.xpath(urlXpath)).getAttribute("href");

        String videoLengthXpath =
            "./div/ytd-rich-grid-media/div[1]/div[1]/ytd-thumbnail/a/div[1]/ytd-thumbnail-overlay-time-status-renderer/div/span";
        String videoLength = element.findElement(By.xpath(videoLengthXpath)).getText();
        // YoutubeCrawlingDto youtubeInstance =

        YoutubeCrawlingDto youtubeInstance =
            new YoutubeCrawlingDto(title, imgSrc, url, videoLength, uploadTime, views);
        youtubeInstances.add(youtubeInstance);
      }
      ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,700)");
      CrawlUtils.stall();
    }
    return null;
  }
}
