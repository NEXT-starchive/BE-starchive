package com.example.starchive.service;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CrawlUtils {
  public static void inputElement(WebDriverWait wait, String xpath, String task, String inputData) {
    WebElement webInput = null;
    try {
      webInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
      stall();
      webInput.click();
    } catch (TimeoutException err) {
      try {
        System.out.print("\nerror " + task + "first click attempt failed\n");
        stall();
        webInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
      } catch (TimeoutException timeout2) {
        System.out.print("\nerror " + task + "is not clickable\n");
      }
    }

    webInput.sendKeys(inputData + Keys.ENTER);
    System.out.print("\n" + task + " completed\n");
  }

  public static void stall() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
    }
  }

  public static ChromeOptions makeConfig() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--headless"); // Browser를 띄우지 않음
    options.addArguments("--disable-gpu"); // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
    options.addArguments("--no-sandbox");
    options.addArguments("--remote-allow-origins=*");
    return options;
  }
}
