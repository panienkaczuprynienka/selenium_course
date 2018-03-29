import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
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

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class HeadersAndLinkTest {

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
  public void headerAndLinkTest() {
    wd.get("http://localhost:8080/litecart/admin/login.php");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();

    Assert.assertTrue(isElementPresentByXpath("//div[@class='logotype']"));
    
//appearance
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(1) > [href]")).click();
    String title1 = "Template";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title1));

    wd.findElement(By.cssSelector("#doc-template .name")).click();
    String title1a = "Template";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title1a));

    wd.findElement(By.cssSelector("#doc-logotype .name")).click();
    String title1b = "Logotype";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title1b));

//catalog
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(2) > [href] .name")).click();
    String title2 = "Catalog";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2));

    wd.findElement(By.cssSelector("#doc-catalog .name")).click();
    String title2a = "Catalog";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2a));

    wd.findElement(By.cssSelector("#doc-product_groups .name")).click();
    String title2b = "Product Groups";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2b));

    wd.findElement(By.cssSelector("#doc-option_groups .name")).click();
    String title2c = "Option Groups";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2c));

    wd.findElement(By.cssSelector("#doc-manufacturers .name")).click();
    String title2d = "Manufacturers";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2d));

    wd.findElement(By.cssSelector("#doc-suppliers .name")).click();
    String title2e = "Suppliers";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2e));

    wd.findElement(By.cssSelector("#doc-delivery_statuses .name")).click();
    String title2f = "Delivery Statuses";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2f));

    wd.findElement(By.cssSelector("#doc-sold_out_statuses .name")).click();
    String title2g = "Sold Out Statuses";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2g));

    wd.findElement(By.cssSelector("#doc-quantity_units .name")).click();
    String title2h = "Quantity Units";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2h));

    wd.findElement(By.cssSelector("#doc-csv .name")).click();
    String title2i = "CSV Import/Export";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2i));

//countries
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(3) > [href] .name")).click();
    String title3 = "Countries";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title3));

    //currencies
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(4) > [href] .name")).click();
    String title4 = "Currencies";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title4));

//customers
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(5) > [href] .name")).click();
    String title5 = "Customers";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title5));

    wd.findElement(By.cssSelector("#doc-customers .name")).click();
    String title5a = "Customers";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title5a));

    wd.findElement(By.cssSelector("#doc-csv .name")).click();
    String title5b = "CSV Import/Export";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title5b));

    wd.findElement(By.cssSelector("#doc-newsletter .name")).click();
    String title5c = "Newsletter";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title5c));

//geozones
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(6) > [href] .name")).click();
    String title6 = "Geo Zones";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title6));

//languages
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(7) > [href] .name")).click();
    String title7 = "Languages";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title7));

    wd.findElement(By.cssSelector("#doc-languages .name")).click();
    String title7a = "Languages";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title7a));

    wd.findElement(By.cssSelector("#doc-storage_encoding")).click();
    String title7b = "Storage Encoding";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title7b));

//modules
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(8) > [href] .name")).click();
    String title8 = "Job Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8));

    wd.findElement(By.cssSelector("#doc-jobs .name")).click();
    String title8a = "Job Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8a));

    wd.findElement(By.cssSelector("#doc-customer .name")).click();
    String title8b = "Customer Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8b));

    wd.findElement(By.cssSelector("#doc-shipping .name")).click();
    String title8c = "Shipping Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8c));

    wd.findElement(By.cssSelector("#doc-payment .name")).click();
    String title8d = "Payment Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8d));

    wd.findElement(By.cssSelector("#doc-order_total .name")).click();
    String title8e = "Order Total Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8e));

    wd.findElement(By.cssSelector("#doc-order_success .name")).click();
    String title8f = "Order Success Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8f));

    wd.findElement(By.cssSelector("#doc-order_action .name")).click();
    String title8g = "Order Action Modules";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title8g));

    //orders
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(9) > [href] .name")).click();
    String title9 = "Orders";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title9));

//pages
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(10) > [href] .name")).click();
    String title10 = "Pages";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title10));

//reports
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(11) > [href] .name")).click();
    String title11 = "Monthly Sales";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title11));

    wd.findElement(By.cssSelector("#doc-monthly_sales .name")).click();
    String title11a = "Monthly Sales";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title11a));

    wd.findElement(By.cssSelector("#doc-most_sold_products .name")).click();
    String title11b = "Most Sold Products";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title11b));

//settings
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(12) > [href] .name")).click();
    String title12 = "Settings";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title12));

//slides
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(13) > [href] .name")).click();
    String title13 = "Slides";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title13));

//tax
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(14) > [href] .name")).click();
    String title14 = "Tax Classes";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title14));

    //translations
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(15) > [href] .name")).click();
    String title15 = "Search Translations";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title15));

    //users
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(16) > [href] .name")).click();
    String title16 = "Users";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title16));

    //vQmods
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(17) > [href] .name")).click();
    String title17 = "vQmods";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title17));
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