import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Proxy_17 extends TestBase {


  @Test
  public void headerAndLinkTest() throws InterruptedException {
    wd.get("http://localhost:8080/litecart/admin/login.php");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();

    Assert.assertTrue(isElementPresentByXpath("//div[@class='logotype']"));

    wd.get("http://localhost:8080/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    wd.findElement(By.xpath("//a[@href='http://localhost:8080/litecart/admin/?app=catalog&doc=catalog&category_id=2']")).click();

    List<WebElement> ducks = wd.findElements(By.xpath("//table[@class='dataTable']//tr//a[contains(text(),'Duck')]"));
    for (int i =0; i<ducks.size(); i++){
      proxy.newHar();
      ducks.get(i).click();
      wd.navigate().back();
      Har har = proxy.endHar();
      har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() + ":" + l.getRequest().getUrl()));
      ducks = wd.findElements(By.xpath("//table[@class='dataTable']//tr//a[contains(text(),'Duck')]"));
    }


  }
/*
@Test
public void getBrowserLogs(){
    proxy.newHar();
    wd.navigate().to("https://selenium2.ru");
    Har har = proxy.endHar();
    har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() + ":" + l.getRequest().getUrl()));
}
*/

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