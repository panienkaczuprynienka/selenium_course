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

import static java.lang.Integer.parseInt;

public class CountriesOrderTest {
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
  public void countriesOrderTest() {
    wd.get("http://localhost:8080/litecart/admin/login.php");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
// 1a
    Assert.assertTrue(isElementPresent("//div[@class='logotype']"));

    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(3) > [href] .name")).click();
    String title3 = "Countries";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title3));
    List<String> countryNames = getCountyNames();
    System.out.println(countryNames);
    checkOrder(countryNames);
    Assert.assertTrue(checkOrder(countryNames) == true);




    //1b
    wd.findElement(By.xpath("//tbody//tr[39]//td[5]")).click();
    checkOrder(getZonesInParticularCountry());




   // if (parseInt(wd.findElement(By.cssSelector("td:nth-of-type(6)")).getAttribute("textContent")) != 0 ){
    //  wd.findElement(By.cssSelector("td:nth-of-type(6)")).click();


     // checkOrder(getZonesInParticularCountry());


/*
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(6) > [href] .name")).click();
    String title6 = "Geo Zones";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title6));
    clickCanada();


    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(6) > [href] .name")).click();
    clickEU();


    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(6) > [href] .name")).click();
    clickUSA();
*/

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

  public List<String> getCountyNames() {
    List<WebElement> elements = wd.findElements(By.cssSelector("td:nth-of-type(5)"));
    List<String> countryNames = new ArrayList<String>();
    for (WebElement element : elements) {
      String countryName = element.getAttribute("textContent");
      countryNames.add(countryName);
    }
    return countryNames;
  }

  public boolean checkOrder(List<String> countryNames) {
    String previous = "";
    for (final String current : countryNames) {
      if (current.compareTo(previous) < 0)
        return false;
      previous = current;
    }
    return true;
  }

  public List<WebElement> getZones() {
    List<WebElement> zones = wd.findElements(By.cssSelector("td:nth-of-type(3)"));
    return zones;
  }


  public List<String> getZonesInParticularCountry() {
    List<WebElement> zonesInParticularCountry = wd.findElements(By.xpath("//table[@id='table-zones']//td[3]"));
    List<String> zonesNames = new ArrayList<String>();
    for (WebElement zone : zonesInParticularCountry) {
      String zoneName = zone.getAttribute("textContent");
      zonesNames.add(zoneName);
    }
    return zonesNames;
  }


  public List<Integer> getNumberInZones() {
    List<WebElement> numberInZones = wd.findElements(By.cssSelector("td:nth-of-type(6)"));
    List<Integer> numberValues = new ArrayList<Integer>();
    for (WebElement number : numberInZones) {
      Integer numberValue = parseInt(number.getAttribute("textContent"));
      numberValues.add(numberValue);

    }
    return numberValues;
  }

  public void checkIfNumberValueIsMultiple(){
    List<Integer> numberValues = getNumberInZones();
    for (Integer number:numberValues){
      if (number>0){

      }
    }
  }


  public void clickCanada() {
    wd.findElement(By.xpath("//a[contains(text(),'Canada')]")).click();
  }

  public void clickEU() {
    wd.findElement(By.xpath("//a[contains(text(),'European Union')]")).click();
  }

  public void clickUSA() {
    wd.findElement(By.xpath("//a[contains(text(),'United States of America')]")).click();
  }

}