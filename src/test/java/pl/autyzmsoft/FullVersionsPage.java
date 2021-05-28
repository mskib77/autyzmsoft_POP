package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class FullVersionsPage {

    private WebDriver driver;

    public FullVersionsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
