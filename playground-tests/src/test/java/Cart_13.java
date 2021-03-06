import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.lang.Integer.parseInt;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Cart_13 {
  WebDriver wd;
  WebDriverWait wait;



  @BeforeMethod
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void cart() throws InterruptedException {
    wd.get("http://localhost:8080/litecart/en/");

    for (int i = 0; i < 3; i++) {
      List<WebElement> products = wd.findElements(By.cssSelector(".product"));
      products.get(i).click();

      WebElement quantityInBasket = wd.findElement(By.cssSelector("#cart [class=\"quantity\"]"));
      Integer quantity = parseInt(quantityInBasket.getText());

      WebElement addToCardButton = wd.findElement(By.cssSelector("[name=\"add_cart_product\"]"));
      if (isElementPresent(wd, By.cssSelector("[name=\"options[Size]\"]"))) {
        select(wd.findElement(By.cssSelector("[name=\"options[Size]\"]")), "Small");
      }
      addToCardButton.click();

      quantity = quantity + 1;

      WebDriverWait wait = new WebDriverWait(wd, 30);

      String quantityString = Integer.toString(quantity);
      WebElement cartQuantityElement = wd.findElement(By.xpath("//span[@class='quantity']"));
      wait.until(textToBePresentInElement(cartQuantityElement, quantityString));
      Assert.assertTrue(isElementPresent(wd, By.xpath("//span[@class='quantity' and contains (text(),'" + quantity + "')]")));
      goHome();
    }
    goToCart();


    int j = countRows();
    for (int i = 0; i < j; i++) {
      WebElement row = wd.findElement(By.xpath("//table[@class='dataTable rounded-corners']//tr//td[contains(text(),'Duck')]"));
      wd.findElement(By.xpath("//button[@value='Remove']")).click();
      if (i < j - 1) {
        WebDriverWait wait = new WebDriverWait(wd, 30);
        wait.until(ExpectedConditions.stalenessOf(row));
      }
    }
  }


  public int countRows() {
    List<WebElement> rows = wd.findElements(By.xpath("//table[@class='dataTable rounded-corners']//tr//td[contains(text(),'Duck')]"));
    if (rows.size() == 3) {
      return 3;
    } else if (rows.size() == 2) {
      return 2;
    } else if (rows.size() == 1) {
      return 1;
    }
    return 0;
  }

  private void goToCart() {
    WebElement goToCartButton = wd.findElement(By.cssSelector("[href$=checkout][class=\"link\"]"));
    goToCartButton.click();
  }

  public void goHome() {
    WebElement goHomeButton = wd.findElement(By.cssSelector("#logotype-wrapper"));
    goHomeButton.click();
  }

  @AfterMethod
  public void tearDown() {
    wd.quit();
  }


  protected void waitUntil(ExpectedCondition condition) {
    new WebDriverWait(wd, 10).until(condition);
  }

  boolean isElementPresent(WebDriver wd, By locator) {
    try {
      wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      return wd.findElements(locator).size() > 0;
    } finally {
      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }
  }

  public void select(WebElement element, String value) {
    Select dropdown = new Select(element);
    dropdown.selectByValue(value);
  }


}