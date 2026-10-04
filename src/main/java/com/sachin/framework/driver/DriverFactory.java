package com.sachin.framework.driver;

import com.sachin.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Locale;

public class DriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        quitDriver();
        ConfigReader config = ConfigReader.getInstance();
        String browser = config.getProperty("browser").toLowerCase(Locale.ROOT);
        WebDriver webDriver = switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        driver.set(webDriver);
        try {
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                    config.getIntProperty("pageLoadTimeout", 45)
            ));
            webDriver.manage().window().maximize();
            LOGGER.info("Started {} browser for environment {}", browser,
                    config.getEnvironment());
        } catch (RuntimeException exception) {
            quitDriver();
            throw exception;
        }
    }

    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized for this thread");
        }
        return currentDriver;
    }

    public static void quitDriver() {

        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
                LOGGER.info("Closed browser for current thread");
            } finally {
                driver.remove();
            }
        } else {
            driver.remove();
        }
    }
}