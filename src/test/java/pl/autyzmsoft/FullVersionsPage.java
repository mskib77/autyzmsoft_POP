package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class FullVersionsPage {

    private WebDriver driver;

    public FullVersionsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@class='cssbutton pw-button-01']")
    private WebElement orderButton1;

    @FindBy(xpath = "//input[@class='cssbutton pw-button-02']")
    private WebElement orderButton2;

    @FindBy(xpath = "//input[@type='checkbox']")
    private List<WebElement> allCheckboxes;


    public WebElement getOrderButton1() {
        return orderButton1;
    }

    public WebElement getOrderButton2() {
        return orderButton2;
    }

    public List<WebElement> getAllCheckboxesList() {
        return allCheckboxes;
    }

}
