package uk.ac.cardiff.mma.application;

import org.junit.Test;
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
public class BookingTests {
    @Value("${local.server.port}")
    private int port;

    @Test
    public void BookingTest() {
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

        driver.findElement(By.id("username")).sendKeys("exampleUser");
        driver.findElement(By.id("password")).sendKeys("secret");
        driver.findElement(By.tagName("button")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertTrue(driver.findElement(By.tagName("h3")).getText().contains("Welcome exampleUser"));

        driver.findElement(By.xpath("/html/body/header/nav/ul/li[3]/a")).click();
        assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Book Your Equipment"));

        driver.findElement(By.xpath("//*[@id=\"e1\"]/div[2]/form/button")).click();
        assertTrue(driver.findElement(By.xpath("/html/body/div[2]/h2")).getText().contains("Choose a date"));
        assertTrue(driver.findElement(By.xpath("/html/body/div[4]/h2")).getText().contains("Choose a time slot"));

//        driver.findElement(By.className("datepicker")).click();
//        driver.findElement(By.xpath("/html/body/div[8]/div[1]/table/tbody/tr[5]/td[6]")).click();
//
//        // December 31st 2021, time slot 1 --> unavailable
//        try {
//            System.out.println(driver.findElement(By.id("t1")).getAttribute("class"));
//            assertTrue(driver.findElement(By.id("t1")).getAttribute("class").equals("timeslot unavailable"));
//        } catch (AssertionError e) {
//            System.out.println("failed");
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            System.out.println(driver.findElement(By.id("t1")).getAttribute("class"));
//            assertTrue(driver.findElement(By.id("t1")).getAttribute("class").equals("timeslot unavailable"));
//        }

        driver.quit();
    }
}