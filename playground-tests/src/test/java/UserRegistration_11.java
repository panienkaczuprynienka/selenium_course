import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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

public class UserRegistration_11 {
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
  public void userRejestration() {
    wd.get("http://localhost:8080/litecart/en/");
    clickRegistrationButton();
    List<String> emailAndPassword = fillForm();
    logOut();
    login(emailAndPassword.get(0), emailAndPassword.get(1));
  }

  @AfterMethod
  public void tearDown() {
    wd.quit();
  }

  public void login(String emailText, String passwordText) {
    WebElement email = wd.findElement(By.cssSelector("[name=\"email\"]"));
    email.click();
    email.clear();
    email.sendKeys(emailText);

    WebElement password = wd.findElement(By.cssSelector("[name=\"password\"]"));
    password.click();
    password.clear();
    password.sendKeys(passwordText);

    WebElement loginButton = wd.findElement(By.cssSelector("[name=\"login\"]"));
    loginButton.click();

  }


  public void clickRegistrationButton() {
    WebElement btn = wd.findElement(By.xpath("//a[contains(text(),'New customers click here')]"));
    btn.click();
  }

  public List<String> fillForm() {
    List<String> emailAndPassword = new ArrayList<String>();
    WebElement firstname = wd.findElement(By.cssSelector("[name=\"firstname\"]"));
    firstname.click();
    firstname.clear();
    firstname.sendKeys("Alicja");

    WebElement lastname = wd.findElement(By.cssSelector("[name=\"lastname\"]"));
    lastname.click();
    lastname.clear();
    lastname.sendKeys("Kot");

    WebElement address1 = wd.findElement(By.cssSelector("[name=\"address1\"]"));
    address1.click();
    address1.clear();
    address1.sendKeys("ul. Pawia");

    WebElement postcode = wd.findElement(By.cssSelector("[name=\"postcode\"]"));
    postcode.click();
    postcode.clear();
    postcode.sendKeys("00-001");

    WebElement city = wd.findElement(By.cssSelector("[name=\"city\"]"));
    city.click();
    city.clear();
    city.sendKeys("Warsaw");

    WebElement email = wd.findElement(By.cssSelector("[name=\"email\"]"));
    email.click();
    email.clear();
    Double d = Math.random() * 1000;
    Integer i = d.intValue();
    String emailName = "AlicjaKot" + i + "@gmail.com";
    email.sendKeys(emailName);

    emailAndPassword.add(emailName);

    WebElement phone = wd.findElement(By.cssSelector("[name=\"phone\"]"));
    phone.click();
    phone.clear();
    phone.sendKeys("500500500");

    String pswd = "Alicja1000";

    WebElement password = wd.findElement(By.cssSelector("[name=\"password\"]"));
    password.click();
    password.clear();
    password.sendKeys(pswd);

    emailAndPassword.add(pswd);

    WebElement confirmPassword = wd.findElement(By.cssSelector("[name=\"confirmed_password\"]"));
    confirmPassword.click();
    confirmPassword.clear();
    confirmPassword.sendKeys(pswd);

    WebElement createBtn = wd.findElement(By.cssSelector("[name=\"create_account\"]"));
    createBtn.click();
    return emailAndPassword;
  }

  public void logOut() {
    WebElement logoutBtn = wd.findElement(By.xpath("(//a[contains(text(),'Logout')])[1]"));
    logoutBtn.click();
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