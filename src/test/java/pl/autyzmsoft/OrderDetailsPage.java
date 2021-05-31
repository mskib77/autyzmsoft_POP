package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderDetailsPage {

    private WebDriver driver;

    public OrderDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tr[last()]/td[2]")
    private WebElement total;

    @FindBy(name = "Submit")
    private WebElement buyButton;

    @FindBy(id = "id_Email")
    private WebElement email;

    @FindBy(id = "id_Email_1")
    private WebElement emailRepeated;


    public int getTotal() {
        String totalStr = total.getText();
        String totalSubstr = totalStr.substring(0,totalStr.indexOf(","));
        return Integer.parseInt(totalSubstr);
    }

    public DotPayPage clickBuyButton() {
        buyButton.click();
        return new DotPayPage(driver);
    }

    public void fillEmails(String emailStr) {
        this.email.sendKeys(emailStr);
        this.emailRepeated.sendKeys(emailStr);
    }

}
