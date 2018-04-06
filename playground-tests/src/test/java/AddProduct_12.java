import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class AddProduct_12 {
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
  public void addProduct() {
    wd.get("http://localhost:8080/litecart/admin/login.php");
    loginAsAdmin();
    catalogClick();
    addNewProductButtonClick();
    //general
    enableRadioButtonClick();
    String linkText = "Duck 1";
    type(wd.findElement(By.cssSelector("input[name=\"name[en]\"]")), linkText);
    type(wd.findElement(By.cssSelector("input[name=\"code\"]")),"00-001");
    chooseCategory("Subcategory");
    select(wd.findElement(By.cssSelector("[name=\"default_category_id\"]")), "2");
    chooseSex("male");
    setQuantity(wd.findElement(By.cssSelector("[name=\"quantity\"]")), "10");
    uploadFile(By.cssSelector("[name=\"new_images[]\"]"), "C:\\Users\\Patrycja\\Desktop\\kaczka.jpg");

    wd.findElement(By.name("date_valid_from")).sendKeys(Keys.HOME + "01012018");
    wd.findElement(By.name("date_valid_to")).sendKeys(Keys.HOME + "01012019");

    //information
    swichTab(wd.findElement(By.cssSelector("[href=\"#tab-information\"]")));
    select(wd.findElement(By.cssSelector("[name=\"manufacturer_id\"]")), "1");
    type(wd.findElement(By.cssSelector("[name=\"keywords\"]")), "duck");
    type(wd.findElement(By.cssSelector("[name=\"short_description[en]\"]")), "Little duck");
    String longText = "Duck is the common name for a large number of species in the waterfowl family Anatidae, which also includes swans and geese. ";
    type(wd.findElement(By.cssSelector(".trumbowyg-editor")), longText);
    type(wd.findElement(By.cssSelector("[name=\"head_title[en]\"]")), "Duck for sale");
    type(wd.findElement(By.cssSelector("[name=\"meta_description[en]\"]")), "duck");

     //price
    swichTab(wd.findElement(By.cssSelector("[href=\"#tab-prices\"]")));
    setQuantity(wd.findElement(By.cssSelector("[name=\"purchase_price\"]")), "15");
    String value = "EUR";
    select(wd.findElement(By.cssSelector("[name=\"purchase_price_currency_code\"]")), value);
    typePrice(value, "20");
    clickSave();

    catalogClick();
    Assert.assertTrue(wd.findElements(By.linkText(linkText)).size()>0);

  }

  public void uploadFile(By locator, String filePath) {
    WebElement uploadFileInput = wd.findElement(locator);
    uploadFileInput.click();
    uploadFileInput.sendKeys(filePath);
  }

  private void clickSave() {
    WebElement saveButton = wd.findElement(By.cssSelector("[type=\"submit\"][name=\"save\"]"));
    saveButton.click();
  }

  private void typePrice(String value, String price) {
    if (value.equals("EUR")) {
      clearAndType(wd.findElement(By.cssSelector("[name=\"prices[EUR]\"]")), price);
    } else if (value.equals("USD")) {
      clearAndType(wd.findElement(By.cssSelector("[name=\"prices[USD]\"]")), price);
    }
  }

  private void type(WebElement element, String text) {
    element.click();
    element.sendKeys(text);
  }

  private void clearAndType(WebElement element, String text) {
    element.click();
    element.sendKeys(text);
  }


  public void select(WebElement element, String value) {
    Select dropdown = new Select(element);
    dropdown.selectByValue(value);
  }

  private void swichTab(WebElement tab) {
    tab.click();
  }

  public void setQuantity(WebElement elementInput, String quantity) {
  clearAndType(elementInput, quantity);
  }

  public void chooseSex(String sex) {
    if (sex.equals("female")) {
      WebElement checkboxFemale = wd.findElement(By.cssSelector("[type=\"checkbox\"][name=\"product_groups[]\"][value=\"1-2\"]"));
      checkboxFemale.click();
    } else if (sex.equals("male")) {
      WebElement checkboxMale = wd.findElement(By.cssSelector("[type=\"checkbox\"][name=\"product_groups[]\"][value=\"1-1\"]"));
      checkboxMale.click();
    } else if (sex.equals("unisex")) {
      WebElement checkboxUnisex = wd.findElement(By.cssSelector("[type=\"checkbox\"][name=\"product_groups[]\"][value=\"1-3\"]"));
      checkboxUnisex.click();
    }
  }


  public void chooseCategory(String category) {
    if (category.equals("RubberDuck")){
    WebElement checkboxRubberDucks = wd.findElement(By.cssSelector("[type=\"checkbox\"][data-priority=\"1\"]"));
    checkboxRubberDucks.click();
    } else if (category.equals("Subcategory")){
    WebElement checkboxSubcategory = wd.findElement(By.cssSelector("[type=\"checkbox\"][data-priority=\"2\"]"));
    checkboxSubcategory.click();
  }
  }

  public void enableRadioButtonClick() {
    WebElement enabledRadioButton = wd.findElement(By.cssSelector("[type=\"radio\"][value=\"1\"]"));
    enabledRadioButton.click();
  }

  public void addNewProductButtonClick() {
    WebElement addProductButton = wd.findElement(By.xpath("//a[contains(text(),' Add New Product')]"));
    addProductButton.click();
  }

  public void catalogClick() {
    wd.findElement(By.cssSelector("#box-apps-menu #app-:nth-of-type(2) > [href] .name")).click();
    String title2 = "Catalog";
    Assert.assertTrue(wd.findElement(By.cssSelector("h1")).getText().equals(title2));
  }

  public void loginAsAdmin() {
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
    Assert.assertTrue(isElementPresent("//div[@class='logotype']"));
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