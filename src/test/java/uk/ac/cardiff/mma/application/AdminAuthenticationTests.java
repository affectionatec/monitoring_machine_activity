package uk.ac.cardiff.mma.application;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminAuthenticationTests {
    @Value("${local.server.port}")
    private int port;

    @Test
    public void AdminAuthenticationTest() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://localhost:" + Integer.toString(port) + "/");

        // Login
        driver.findElement(By.id("username")).sendKeys("exampleAdmin");
        driver.findElement(By.id("password")).sendKeys("secret");
        driver.findElement(By.tagName("button")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Correct landing page on successful login
        assertTrue(driver.findElement(By.tagName("h3")).getText().contains("Welcome exampleAdmin"));
        assertTrue(driver.findElement(By.className("table-bordered")).isDisplayed());

        // Successful Logout
        driver.findElement(By.xpath("/html/body/header/nav/ul/li[12]/form/button")).click();
        assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Login"));

        driver.quit();
    }

    @Test
    public void SuperAdminAuthenticationTest() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://localhost:" + Integer.toString(port) + "/");

        // Login
        driver.findElement(By.id("username")).sendKeys("exampleSuperadmin");
        driver.findElement(By.id("password")).sendKeys("secret");
        driver.findElement(By.tagName("button")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Correct landing page on successful login
        assertTrue(driver.findElement(By.tagName("h3")).getText().contains("Welcome exampleSuperadmin"));
        assertTrue(driver.findElement(By.className("table-bordered")).isDisplayed());

        // Successful authorization to user management endpoint
        driver.findElement(By.xpath("/html/body/header/nav/ul/li[11]/a")).click();
        assertTrue(driver.findElement(By.tagName("h1")).getText().contains("user list"));

        // Successful Logout
        driver.findElement(By.xpath("/html/body/header/nav/ul/li[12]/form/button")).click();
        assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Login"));

        driver.quit();
    }
}