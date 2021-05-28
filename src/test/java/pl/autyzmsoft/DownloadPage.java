package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DownloadPage {
    private WebDriver driver;

    public DownloadPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
