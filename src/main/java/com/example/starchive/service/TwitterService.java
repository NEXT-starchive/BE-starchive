package com.example.starchive.service;

import com.example.starchive.dto.GoodsResDto;
import com.example.starchive.dto.TwitterResDto;
import com.example.starchive.dto.crawlingdto.TweetsCrawlingDto;
import com.example.starchive.entity.Goods;
import com.example.starchive.entity.Twitter;
import com.example.starchive.repository.GoodsRepository;
import com.example.starchive.repository.TwitterRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import java.util.List;
@Service
@RequiredArgsConstructor
public class TwitterService implements CrawlingData{


    private final TwitterRepository twitterRepository;


    @Override
    public Page<TwitterResDto> getData(Pageable pageable) {
        Page<Twitter> goodsList = twitterRepository.findAll(pageable);
        Page<TwitterResDto> goodsDtoList = goodsList.map(twit -> new TwitterResDto(twit));
        return goodsDtoList;
    }

  // content has multiple elements(icons, a, span sort) this is for sorting and marking
  public String crawlContent(List<WebElement> div) {
    StringBuilder contents = new StringBuilder();
    Boolean first = true;

    for (WebElement element : div) {
      if (first) {
        first = false;
        continue;
      }
      // Check the tag of the element and act accordingly
      if (element.getTagName().equals("img")) {
        // mark the img(icons) with `at front to acknowledge
        contents
            .append("`")
            .append(element.getAttribute("src"))
            .append("`"); // Add the src attribute of the img
      } else {
        // else just get the text if it has #it is considered as <a>
        contents.append(element.getText());
      }
    }
    return contents.toString().trim();
    }
    public void crawlData()  {

        String chromeDriverPath = "C:/Users/Liam/Downloads/chromedriver_win64/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");          // 최대크기로
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless");                 // Browser를 띄우지 않음
        options.addArguments("--disable-gpu");              // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver( options );
        driver.get("https://twitter.com/bts_bighit");
        //wait for at least 10 sec
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    //        WebDriverWait waitReload = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait and click login
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"layers\"]/div/div[1]/div/div/div/div[2]/div[2]/div/div/div[1]/a"))).click();
        //login to twitter
        login(wait);
        //proceeding to crawl newest bts
        try {
            Thread.sleep(6000); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div")));
        //preparing for storing data
        Set<String> uniqueTexts = new HashSet<>();
        List<TweetsCrawlingDto> results = new ArrayList<>();


        try{
            while (true) {
                List<WebElement> tweets = driver.findElements(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div"));

                for (int i = 0; i < tweets.size(); i++) {
                    // Re-find the tweet element by index each time through the loop
                    tweets = driver.findElements(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div"));
                    WebElement tweet = tweets.get(i);
                    String text = tweet.getText();

                    if (text.contains("BTS_official") && !uniqueTexts.contains(text)) {
                        //finding out time
                        String timeString = tweet.findElement(By.tagName("time")).getAttribute("datetime");
                        LocalDateTime uploadTimeLocal = getTime(timeString);
                        // Get today's date at UTC and check
                        LocalDate todayUtc = LocalDate.now(ZoneId.of("UTC"));
                        boolean isToday = uploadTimeLocal.toLocalDate().equals(todayUtc);
                        if (!isToday) {
                            System.out.print("\ntoday's tweet ended\n");
//                            break;
                        }
                        else{
                            System.out.print("\ngood\n");
                        }

                        //go to tweet block and get below contents(span, img)
                        WebElement tweetContent = tweet.findElement(By.xpath("./div/div/article/div/div/div[2]/div[2]/div[2]"));
                        List<WebElement> belowContents = tweetContent.findElements(By.xpath(".//*"));
                        //get the content tweet
                        String content = crawlContent(belowContents);
                        //add to unique for blocking duplicate
                        uniqueTexts.add(text);
                        //we need only one img
                        List<WebElement> images = tweet.findElement(By.xpath("./div/div/article/div/div/div[2]/div[2]/div[3]"))
                                .findElements(By.tagName("img"));
                        String img = null;
                        if (!images.isEmpty()) {
                            img = images.get(0).getAttribute("src");
                        }
                        tweetContent.click();
                        String url = driver.getCurrentUrl();
                        driver.navigate().back();
                        //reset
                        i=0;
                        //add to array
                        if(results.size() < 4)
                            results.add(new TweetsCrawlingDto(content, url, img, uploadTimeLocal));
                        else break;
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div")));
                    }
                }
                if (results.size() < 4) {
                    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,700)");
                    new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                            wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
                } else break;
            }
        } finally {
           driver.close();
        }
        for (int i = 0; i < results.size(); i++) {
            TweetsCrawlingDto tweet = results.get(i);
            System.out.println("Index " + i + ": " + tweet);
            tweet.toEntity();
        }
        System.out.print("done");
    }
    private LocalDateTime getTime(String timeString){
        ZonedDateTime utcDateTime = ZonedDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME);
        // Convert the ZonedDateTime to Korea time zone
        return utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
    private void login(WebDriverWait wait){
        //putting in my email
        String emailXpath = "//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[5]/label/div/div[2]/div/input";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(emailXpath)))
                .sendKeys("hyukjun1111@gmail.com" + Keys.ENTER);
        //putting in my username
        String userNameXpath = "//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[1]/div/div[2]/label/div/div[2]/div/input";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(userNameXpath)))
                .sendKeys("@ihyeogj24056742" + Keys.ENTER);
        //putting in my pw
        String pwXpath = "//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[1]/div/div/div[3]/div/label/div/div[2]/div[1]/input";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(pwXpath)))
                .sendKeys("testtest!" + Keys.ENTER);
    }
}