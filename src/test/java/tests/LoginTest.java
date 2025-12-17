package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void validLoginTest() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector(".radius")).click();

        String successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash"))).getText();
        Assert.assertTrue(successMsg.contains("You logged into a secure area!"), "Login success message not found!");
    }

    @Test // Invalid Login Test
    public void invalidLoginTest() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("invalidUser");
        driver.findElement(By.id("password")).sendKeys("invalidPass");
        driver.findElement(By.cssSelector(".radius")).click();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
