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

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ProductAttributesTest_10 {
  WebDriver wd;
  WebDriverWait wait;



  @BeforeMethod
  public void setUp() throws Exception {

      wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void productAttributes() {
    wd.get("http://localhost:8080/litecart/en/");
    Assert.assertTrue(isElementPresent("//div[@id='logotype-wrapper']"));


    WebElement campaignBox = wd.findElement(By.cssSelector("#box-campaigns"));
    WebElement firstProduct = campaignBox.findElements(By.cssSelector("#box-campaigns [class='link'][title*=\"Duck\"]")).get(0);
    String nameOfProduct = firstProduct.findElement(By.cssSelector(".name")).getText();

    String regularPrice = firstProduct.findElement(By.cssSelector(".regular-price")).getText();
    String clearRegularPrice = regularPrice.replaceAll("[^\\d]", "");
    int regularPriceInt = Integer.parseInt(clearRegularPrice);
    String regularPriceColor = firstProduct.findElement(By.cssSelector(".regular-price")).getCssValue("color");
    String regularPriceDecoration = firstProduct.findElement(By.cssSelector(".regular-price")).getCssValue("text-decoration");

    System.out.println("regularPriceColor ->"+regularPriceColor +"regularPriceDecoration ->"+ regularPriceDecoration);


    String campaignPrice = firstProduct.findElement(By.cssSelector(".campaign-price")).getText();
    String clearCampaignPrice = campaignPrice.replaceAll("[^\\d]", "");
    int campaignPriceInt = Integer.parseInt(clearCampaignPrice);
    String campaignPriceColor = firstProduct.findElement(By.cssSelector(".regular-price")).getCssValue("color");
    String campaignPriceWeight = firstProduct.findElement(By.cssSelector(".regular-price")).getCssValue("font-weight");

    System.out.println("campaignPriceColor ->"+campaignPriceColor+"campaignPriceWeight ->"+campaignPriceWeight);

    firstProduct.click();
    String productPageName = wd.findElement(By.cssSelector("#box-product [class=\"title\"]")).getText();
    Assert.assertTrue(nameOfProduct.equals(productPageName));

    String productPageRegularPrice = wd.findElement(By.cssSelector("#box-product [class=\"regular-price\"]")).getText().replaceAll("[^\\d]", "");
    int productPageRegularPriceInt = Integer.parseInt(productPageRegularPrice);
    Assert.assertTrue(productPageRegularPriceInt==regularPriceInt);
    String productPageRegularPriceColor = wd.findElement(By.cssSelector("#box-product [class=\"regular-price\"]")).getCssValue("color");
    String productPageRegularPriceDecoration = wd.findElement(By.cssSelector("#box-product [class=\"regular-price\"]")).getCssValue("text-decoration");

    System.out.println("productPageRegularPriceColor -> "+productPageRegularPriceColor
            + "productPageRegularPriceDecoration -> "+ productPageRegularPriceDecoration);

    String productPageCampaignPrice = wd.findElement(By.cssSelector("#box-product [class=\"campaign-price\"]")).getText().replaceAll("[^\\d]", "");
    int productPageCampaignPriceInt = Integer.parseInt(productPageCampaignPrice);
    Assert.assertTrue(productPageCampaignPriceInt==campaignPriceInt);
    String productPageCampaignPriceColor = wd.findElement(By.cssSelector("#box-product [class=\"campaign-price\"]")).getCssValue("color");
    String productPageCampaignPriceWeight = wd.findElement(By.cssSelector("#box-product [class=\"campaign-price\"]")).getCssValue("font-weight");

    System.out.println("productPageCampaignPriceColor -> "+ productPageCampaignPriceColor
            + "productPageCampaignPriceWeight -> " + productPageCampaignPriceWeight);

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