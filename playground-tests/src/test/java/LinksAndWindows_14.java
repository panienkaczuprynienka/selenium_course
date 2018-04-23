import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LinksAndWindows_14 {


  WebDriver wd;
  WebDriverWait wait;



  @BeforeMethod
  public void setUp() throws Exception {

      wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void login() throws InterruptedException {
    wd.get("http://localhost:8080/litecart/admin/login.php");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();

    Assert.assertTrue(isElementPresent("//div[@class='logotype']"));

    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(3) > [href] .name")).click();
    String title3 = "Countries";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title3));

    wd.findElement(By.cssSelector("[title *= Edit]")).click();

    List<WebElement> links = wd.findElements(By.xpath("//i[@class='fa fa-external-link']"));

    for (int i = 0; i < links.size(); i++) {
      String mainWindow = wd.getWindowHandle();
      System.out.println(mainWindow);
      Set<String> oldWindows = wd.getWindowHandles();
      links.get(i).click();
      wait = new WebDriverWait(wd, 10);
      String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
      wd.switchTo().window(newWindow);
      System.out.println(wd.getTitle());
      wd.close();
      wd.switchTo().window(mainWindow);
    }
  }


  public ExpectedCondition<String> thereIsWindowOtherThan(Set<String> oldWindows) {
    return input -> {
      Set<String> handles = input.getWindowHandles();
      handles.removeAll(oldWindows);
      return handles.size() > 0 ? handles.iterator().next() : null;
    };
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
