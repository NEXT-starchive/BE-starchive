package com.example.starchive.service;

import com.example.starchive.dto.GoodsResDto;
import com.example.starchive.dto.crawlingdto.GoodsCrawlingDto;
import com.example.starchive.dto.crawlingdto.YoutubeCrawlingDto;
import com.example.starchive.entity.Goods;
import com.example.starchive.repository.GoodsRepository;
import com.example.starchive.dto.crawlingdto.GoodsCrawlingDto;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.openqa.selenium.NoSuchElementException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService implements CrawlingData {

  private final GoodsRepository goodsRepository;

  @Override
  public Page<GoodsResDto> getData(Pageable pageable) {
    Page<Goods> goodsList = goodsRepository.findAllByOrderByUploadTimeDesc(pageable);
    Page<GoodsResDto> goodsDtoList = goodsList.map(good -> new GoodsResDto(good));
    return goodsDtoList;
  }

  public void crawlData() {
    // chrome path already in starchiveApp
    ChromeDriver driver = new ChromeDriver(CrawlUtils.makeConfig());
    driver.get(
        "https://m.bunjang.co.kr/search/products?category_id=910&order=score&q=%EB%B0%A9%ED%83%84%EC%86%8C%EB%85%84%EB%8B%A8");

    new WebDriverWait(driver, Duration.ofSeconds(15))
        .until(
            webDriver ->
                ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
                    .equals("complete"));
    CrawlUtils.stall();

    String goodsXpath = "/html/body/div[1]/div/div/div[4]/div/div[4]/div/div";
    List<WebElement> goodsStack = driver.findElements(By.xpath(goodsXpath));
    Integer count = 0;
    List<GoodsCrawlingDto> goodsInstances = new ArrayList<>();
    for (WebElement goods : goodsStack) {
      if (count > 10) break;
      String shop = "번개장터";
      String time;
      try {
        time = goods.findElement(By.xpath("./a/div[2]/div[2]/div[2]")).getText();
      } catch (NoSuchElementException err) {
        continue;
      }

      String area = goods.findElement(By.xpath("./a/div[3]")).getText();
      String title = goods.findElement(By.xpath("./a/div[2]/div[1]")).getText();
      System.out.print("\narea: " + area + ": " + title + "\n");
      // removes ad
      if (time.contains("AD") || area.contains("광고")) continue;
      LocalDateTime uploadTime = calculateUploadTime(time);
      String url = goods.findElement(By.xpath("./a")).getAttribute("href");
      String src = goods.findElement(By.tagName("img")).getAttribute("src");
      String price = goods.findElement(By.xpath("./a/div[2]/div[2]/div[1]")).getText();
      GoodsCrawlingDto goodsInstance =
          new GoodsCrawlingDto(shop, price, title, src, url, area, uploadTime);
      goodsInstances.add(goodsInstance);
      count += 1;
    }
    for (int i = 0; i < goodsInstances.size(); i++) {
      GoodsCrawlingDto goodsInstance = goodsInstances.get(i);
      System.out.print("Index " + i + ": " + goodsInstance);
      Goods instance = goodsInstance.toEntity();
      goodsRepository.save(instance);
    }
    System.out.print("\ndone\n");
    driver.close();
  }

  private LocalDateTime calculateUploadTime(String time) {
    int interval;
    if (time.contains("초")) interval = 0;
    else interval = Integer.parseInt(time.replaceAll("\\D+", ""));

    ZoneId zoneId = ZoneId.of("Asia/Seoul");
    LocalDateTime uploadTime = LocalDateTime.now(zoneId).minusMinutes(interval);
    return uploadTime;
  }
}
