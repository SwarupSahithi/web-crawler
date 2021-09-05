import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.stream.IntStream;

/**
 * Project Name    : web-crawler
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 9/4/2021
 * Time            : 1:57 PM
 * Description     :
 **/

public class WebCrawler {

    public void crawl(String url, int numberOfCrawls) {
        IntStream.rangeClosed(1, numberOfCrawls).forEach(i -> {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("start-maximized");
            WebDriver driver = new ChromeDriver(chromeOptions);

            driver.get(url);
            new WebDriverWait(driver, 60)
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
            System.out.println(i + " attempt of crawling to '" + url + "'");
            driver.quit();
        });
    }
}
