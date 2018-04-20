import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class TestBase {
  public EventFiringWebDriver wd;
  public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal();
  public WebDriverWait wait;

  public BrowserMobProxy proxy;

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

    proxy = new BrowserMobProxyServer();
    proxy.start(0);
    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

    DesiredCapabilities cap = DesiredCapabilities.chrome();
    //Proxy proxy = new Proxy();
    //proxy.setHttpProxy("my proxy:8888");
    cap.setCapability(CapabilityType.PROXY, seleniumProxy);
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


  @AfterMethod
  public void tearDown() {
    wd.quit();
  }



}