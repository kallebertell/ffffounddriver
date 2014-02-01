package ffffounddriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {

    public static void main(String[] args) throws Exception {
        WebDriver driver = new FirefoxDriver();
        new FfffoundCrawler(driver).crawl();
    }

}
