import org.openqa.selenium.WebDriver;

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

    private final WebDriver driver;

    public WebCrawler(WebDriver driver) {
        this.driver = driver;
    }

    public void crawl(String url, int numberOfCrawls) {
        IntStream.rangeClosed(1, numberOfCrawls).forEach(i -> {
            driver.get(url);
            System.out.println(i + " attempt of crawling to '" + url + "'");
        });
    }
}
