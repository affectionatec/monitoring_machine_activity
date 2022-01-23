package uk.ac.cardiff.mma.application;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainingRequestTests {

    private WebDriver webDriver;

    // Get the Spring test framework to configure the server port it is using for the test.
    @Value("${local.server.port}")
    private int port;

    @Test
    public void UserRequestTrainingContentsChromedriverTesting() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // WebDriverManager is a library which allows to automate the management of the drivers (e.g. chromedriver, etc.) required by Selenium WebDriver.
        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        this.webDriver = new ChromeDriver(options);

        // Log in as user.
        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/");
        this.webDriver.findElement(By.id("username")).sendKeys("exampleUser");
        this.webDriver.findElement(By.id("password")).sendKeys("secret");
        this.webDriver.findElement(By.tagName("button")).click();
        this.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        assertTrue(webDriver.findElement(By.tagName("h3")).getText().contains("Welcome exampleUser"));

        // Click the Booking button on the nav.
        this.webDriver.findElement(By.xpath("/html/body/header/nav/ul/li[3]/a")).click();
//        assertTrue(webDriver.findElement(By.tagName("article")).getText().contains("Request Training"));

        // Click the "Request Training" button.
        this.webDriver.findElement(By.xpath("/html/body/section/article[5]/button")).click();
        assertTrue(webDriver.switchTo().alert().getText().contains("please wait for approve."));
        // Used to click on the "OK" button of the alert.
        this.webDriver.switchTo().alert().accept();

        // Closes all the browser windows and terminates the WebDriver session.
        webDriver.quit();
    }

    @Test
    public void AdminApproveOrDenyTrainingRequestContentsChromedriverTesting() {
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
        this.webDriver = new ChromeDriver(options);

        // Log in as admin.
        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/");
        this.webDriver.findElement(By.id("username")).sendKeys("exampleAdmin");
        this.webDriver.findElement(By.id("password")).sendKeys("secret");
        this.webDriver.findElement(By.tagName("button")).click();
        this.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        assertTrue(webDriver.findElement(By.tagName("h3")).getText().contains("Welcome exampleAdmin"));

        // Find the Alerts button on the nav.
        assertTrue(webDriver.findElement(By.xpath("/html/body/header/nav/ul/li[10]/a")).getText().contains("Alerts"));

//        // Click the Alerts button on the nav.
//        this.webDriver.findElement(By.xpath("/html/body/header/nav/ul/li[10]/a")).click();
//        assertTrue(webDriver.findElement(By.tagName("table")).getText().contains("caitlin"));

        webDriver.quit();
    }

}
