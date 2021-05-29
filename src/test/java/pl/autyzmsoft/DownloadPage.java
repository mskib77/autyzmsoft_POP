package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class DownloadPage {

    private WebDriver driver;

    public DownloadPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "id_Email")
    private WebElement emailField;

    @FindBy(id = "bWyslij")
    private WebElement sendButton;

    @FindBy(id = "links_sent_1")
    private WebElement mailSendInfo;

    @FindBy(id = "email_sent_1")
    private WebElement addressee;

    @FindBy(id = "email_err_1")
    private WebElement errorInfo;

    public void fillEmail(String email) {
        emailField.sendKeys(email);
    }

    public void sendEmail() {
        sendButton.click();
    }

    public String getMailSentInfoText() {
        return mailSendInfo.getText();
    }

    public String getAddresseeText() {
        return addressee.getText();
    }

    public String getErrorInfoText() {
         return errorInfo.getText();
    }

}
