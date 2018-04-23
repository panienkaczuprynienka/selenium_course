import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class StickerTest_08 {
  WebDriver wd;
  WebDriverWait wait;



  @BeforeMethod
  public void setUp() throws Exception {

      wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void stickerTest() {
    wd.get("http://localhost:8080/litecart/en/");

    Assert.assertTrue(isElementPresent("//div[@id='logotype-wrapper']"));

    int i = getCountOfStickers();
    System.out.println("The number of stickers is " + i + "");

    List<WebElement> ducks = wd.findElements(By.className("product"));
    for (WebElement duck : ducks) {
      int quantityOfSticksPerDuck = duck.findElements(By.className("sticker")).size();
      Assert.assertTrue(quantityOfSticksPerDuck == 1);
    }

  }

  @AfterMethod
  public void tearDown() {
    wd.quit();
  }

  public static boolean isAlertPresent(FirefoxDriver wd) {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public int getCountOfStickers() {
    List<WebElement> elements = wd.findElements(By.className("sticker"));
    return elements.size();
  }

  public boolean isElementPresent(String xPathLocator) {
    try {
      wd.findElement(By.xpath(xPathLocator));
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }


}