package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DotPayPage {

    private WebDriver driver;

    public DotPayPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "kwotas")
    private WebElement amount;

    public int getAmount() {
        String amountStr = amount.getText();
        String amountSubstr = amountStr.substring(0,amountStr.indexOf("."));
        return (Integer.parseInt(amountSubstr));
    }


}
