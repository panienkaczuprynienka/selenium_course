import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

  public class AdminLoginTest {
    WebDriver wd;
    String browser= "firefox";


    @BeforeMethod
    public void setUp() throws Exception {
      if (browser.equals("firefox")) {
        wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
      } else if (browser.equals("chrome")) {
        wd = new ChromeDriver();
      } else if (browser.equals("IE")) {
        wd = new InternetExplorerDriver();
      }
      wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    public void login() {
      wd.get("http://localhost:8080/litecart/admin/login.php");
      wd.findElement(By.name("username")).sendKeys("admin");
      wd.findElement(By.name("password")).sendKeys("admin");
      wd.findElement(By.name("login")).click();
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


  }


