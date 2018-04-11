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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class HeadersAndLinkTest_07 {

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
  public void headerAndLinkTest() throws InterruptedException {
    wd.get("http://localhost:8080/litecart/admin/login.php");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();

    Assert.assertTrue(isElementPresentByXpath("//div[@class='logotype']"));
    chooseMenuLinks();
  }

  public void chooseMenuLinks() throws InterruptedException {


    List <WebElement> mainLinks = wd.findElements(By.cssSelector("#app-"));
    Integer mainLinksQuantity = mainLinks.size();


    for (int i = 1; i < mainLinksQuantity + 1; i++) {

      WebElement currentMainLink = wd.findElement(By.cssSelector("#app-:nth-child(" + i + ")"));
      String mainLinkName = currentMainLink.getText();
      System.out.println(mainLinkName);
      currentMainLink.click();

      Assert.assertTrue(!headerValue().isEmpty());

      chooseSubMenuLinks();
    }
  }

  private void chooseSubMenuLinks() throws InterruptedException {

    List <WebElement> subMenuLinks = wd.findElements(By.cssSelector("[id^=doc-]"));
    Integer subMenuLinksQuantity = subMenuLinks.size();

    for (int i = 1; i < subMenuLinksQuantity + 1; i++) {
      WebElement currentSubMenuLink = wd.findElement(By.cssSelector("[id^=doc-]:nth-child(" + i + ")"));
      String subMenuItemName = currentSubMenuLink.getText();
      System.out.println(subMenuItemName);
      currentSubMenuLink.click();

      Assert.assertTrue(!headerValue().isEmpty());
    }
  }


  public String headerValue() {
    return wd.findElement(By.tagName("h1")).getText();
  }

  public boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }





  @AfterMethod
  public void tearDown() {
    wd.quit();
  }


  public boolean isElementPresentByXpath(String xpathLocator) {
    try {
      wd.findElement(By.xpath(xpathLocator));
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  public boolean isElementPresentByCss(String cssLocator) {
    try {
      wd.findElement(By.cssSelector(cssLocator));
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

}