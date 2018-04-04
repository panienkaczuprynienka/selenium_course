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
  String browser = "chrome";
  String runningSchemaForFF = "new";


  @BeforeMethod
  public void setUp() throws Exception {
    if (browser.equals("firefox")) {
      if (runningSchemaForFF.equals("new")) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, false);
        wd = new FirefoxDriver(caps);
        System.out.println(((HasCapabilities) wd).getCapabilities());
        wait = new WebDriverWait(wd, 10);
      } else if (runningSchemaForFF.equals("new")) {
        wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
      }
    } else if (browser.equals("chrome")) {
      wd = new ChromeDriver();
    } else if (browser.equals("IE")) {
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void stickerTest() {
    wd.get("http://localhost:8080/litecart/en/");

    Assert.assertTrue(isElementPresent("//div[@id='logotype-wrapper']"));
    List<WebElement> products = getProducts();
    checkStickerForEveryProduct(products);

    int i = getCountOfStickers();
    System.out.println("The number of stickers is " + i + "");
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
    List<WebElement> elements = wd.findElements(By.xpath("//li[@class='product column shadow hover-light']//div[contains(@class,'sticker')]"));
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

  public List<WebElement> getProducts() {
    List<WebElement> products = wd.findElements(By.xpath("//div[contains(text(),'Duck')]/../.."));
    return products;
  }

  public List<WebElement> checkStickerForEveryProduct(List<WebElement> products) {
    List<WebElement> stickers = new ArrayList<WebElement>();
    for (WebElement product : products) {
      WebElement sticker = product.findElement(By.xpath("//div[contains(@class,'sticker')]"));
      Assert.assertFalse(sticker == null);
      stickers.add(sticker);
    }
    return stickers;
  }
}