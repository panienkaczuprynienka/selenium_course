import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogEntries;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;


public class Logs_17 {
  public EventFiringWebDriver wd;
  public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal();
  public WebDriverWait wait;
  public LogEntries log;

  public static class MyListener extends AbstractWebDriverEventListener {
    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
      System.out.println(by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
      System.out.println(by + "found");
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
      System.out.println(throwable);
    }
  }


  @BeforeMethod
  public void setUp() throws Exception {
    if (tlDriver.get() != null) {
      wd = tlDriver.get();
      wait = new WebDriverWait(wd, 10);
      return;
    }

    // start the proxy
    BrowserMobProxy proxy = new BrowserMobProxyServer();
    proxy.start(0);

    // get the Selenium proxy object
    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

    // configure it as a desired capability
    DesiredCapabilities capabilities = new DesiredCapabilities();
   capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

    DesiredCapabilities cap = DesiredCapabilities.chrome();
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
    cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    wd = new EventFiringWebDriver(new ChromeDriver(cap));
    wd.register(new MyListener());
    tlDriver.set(wd);
    wait = new WebDriverWait(wd, 10);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      wd.quit();
      wd = null;
    }));

    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void logs() {
    wd.get("http://localhost:8080/litecart/en/");

    Assert.assertTrue(isElementPresent("//div[@id='logotype-wrapper']"));
    System.out.println(wd.manage().logs().getAvailableLogTypes());
    wd.manage().logs().get("performance").forEach(l -> System.out.println(l));
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


  public boolean isElementPresent(String xPathLocator) {
    try {
      wd.findElement(By.xpath(xPathLocator));
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }


}