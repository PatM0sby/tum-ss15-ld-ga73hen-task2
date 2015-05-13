package edu.tum.cs.aicos.linkeddata.training.html;

import edu.tum.cs.aicos.linkeddata.training.Application;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class HtmlControllerTest {

    Logger logger = LoggerFactory.getLogger(HtmlControllerTest.class);

    @Value("${local.server.port}")
    protected int port;

    @Test
    public void testHomePage() throws Exception {
        logger.debug("testHello begin");

        WebDriver browser = new FirefoxDriver();
        try {
            browser.navigate().to("http://127.0.0.1:" + port + "/");
            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            String highpopulation = "1000000000";
             WebElement textbox;
             textbox = browser.findElement(By.id("input"));
             textbox.sendKeys(highpopulation);
             browser.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
             WebElement button;
             button = browser.findElement(By.id("button"));
             button.click();
             browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

             org.junit.Assert.assertEquals(browser.getPageSource().contains("China"), true);
        } finally {
            browser.quit();
        }

        logger.debug("testHello end");
    }

}
