package ffffounddriver;

import org.openqa.selenium.*;

import java.util.*;

public class FfffoundCrawler {

    private final WebDriver driver;
    private final Random rnd;
    private final Set<String> visitedUrls;

    public FfffoundCrawler(WebDriver driver) {
        this.driver = driver;
        this.rnd = new Random();
        this.visitedUrls = new HashSet<String>();
    }

    public void crawl() {
        goFullScreen();

        openFrontPage();
        getRandomFrontPageImageLink().click();

        for (int i=0; i<100; i++) {
            visitNextImage();
            rememberThatWeSawThisPage();
            admireImageForTenSeconds();
        }

        closeTheBrowser();
    }

    private void openFrontPage() {
        driver.get("http://ffffound.com");
    }

    private void goFullScreen() {
        driver.manage().window().maximize();
        ((JavascriptExecutor) driver).executeScript("window.focus();");
    }

    private void visitNextImage() {
        try {
            getRandomImageLink().click();
        } catch (NoUnseenImagesException e) {
            openFrontPage();
            getRandomFrontPageImageLink().click();
        }
    }

    private void rememberThatWeSawThisPage() {
        visitedUrls.add(driver.getCurrentUrl());
    }

    private void admireImageForTenSeconds() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    private WebElement getRandomFrontPageImageLink() {
        return randomUnseenLink(driver.findElements(By.cssSelector(".asset td a")));
    }

    private WebElement getRandomImageLink() {
        List<WebElement> imageLinks = new ArrayList<WebElement>();
        imageLinks.addAll(driver.findElements(By.cssSelector(".related_to_item td a")));
        imageLinks.addAll(driver.findElements(By.cssSelector(".more_images_item td a")));
        return randomUnseenLink(imageLinks);
    }

    private WebElement randomUnseenLink(List<WebElement> elements) {
        WebElement el = randomElement(elements);

        int tries = 0;

        boolean hasSeenPage = visitedUrls.contains(el.getAttribute("href"));

        while (hasSeenPage) {
            el = randomElement(elements);
            tries++;

            if (tries > 100) {
                throw new NoUnseenImagesException();
            }
        }

        return el;
    }

    private <T> T randomElement(List<T> elements) {
        int rndIndex = rnd.nextInt(elements.size());
        return elements.get(rndIndex);
    }

    private void closeTheBrowser() {
        driver.quit();
    }

    public static class NoUnseenImagesException extends RuntimeException {
    }
}
