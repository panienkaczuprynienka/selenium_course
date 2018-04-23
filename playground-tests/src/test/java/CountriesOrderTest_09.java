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

public class CountriesOrderTest_09 {
  WebDriver wd;
  WebDriverWait wait;



  @BeforeMethod
  public void setUp() throws Exception {

      wd = new ChromeDriver();
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
    String title1 = "Countries";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title1));
    List<String> countryNames = getCountyNames();
    System.out.println(countryNames);
    checkOrder(countryNames);
    Assert.assertTrue(checkOrder(countryNames));

//1b
    for (int i = 0; i < countryNames.size(); i++) {
      List<String> zonesListNames = new ArrayList();
      List<WebElement> zones = wd.findElements(By.xpath("//tr[@class='row']"));

      if (Integer.parseInt(zones.get(i).findElement(By.xpath("./td[6]")).getText()) > 0) {

        zones.get(i).findElement(By.cssSelector("[title=\"Edit\"]")).click();
        Assert.assertTrue(isElementPresent("//h1[contains(text(),'Edit Country')]"));

        List<WebElement> namesOfZones = wd.findElements(By.xpath("//table[@id='table-zones']//td[descendant::*[contains(@name,'name')]]"));
        for (WebElement nameOfZone : namesOfZones) {
          zonesListNames.add(nameOfZone.getText());
        }
        zonesListNames.remove(zonesListNames.size() - 1);
        Assert.assertTrue(checkOrder(zonesListNames));
        wd.findElement(By.xpath("//span[contains(text(),'Countries')]")).click();
      }
    }


    //2
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(6) > [href] .name")).click();
    String title2 = "Geo Zones";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2));

    List<WebElement> countries = wd.findElements(By.cssSelector("[name] td:nth-of-type(3)"));
    int quantity = countries.size();
    for (int i = 0; i < quantity; i++) {

      WebElement country = countries.get(i);
      country.findElements(By.cssSelector("a[href^=http]")).get(0).click();

      List<WebElement> zones = wd.findElements(By.xpath("//table[@id='table-zones']//select[contains(@name,'zone_code')]/option[@selected='selected']"));
      List<String> zonesNames = new ArrayList<String>();
      for (WebElement zone : zones) {
        zonesNames.add(zone.getText());
      }
      Assert.assertTrue(checkOrder(zonesNames));

      wd.navigate().back();
      countries = wd.findElements(By.cssSelector("[name] td:nth-of-type(3)"));
    }

  }

  @AfterMethod
  public void tearDown() {
    wd.quit();
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

}