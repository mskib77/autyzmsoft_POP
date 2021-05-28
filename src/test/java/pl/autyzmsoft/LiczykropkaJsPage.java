package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.List;

public class LiczykropkaJsPage {
    private WebDriver driver;

    public LiczykropkaJsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "klawisz-klasa")
    private List<WebElement> klawiszeLK;

    @FindBy(xpath = "//div[@class='div-liczba-klasa div-liczba-klasa-growing']")
    private WebElement liczbaLK;

    @FindBy(id = "bDalej")
    WebElement greenButtonLK;


    /***
     * Returns buttons with numbers as generated on Liczykropka screen
     */
    public List<WebElement> getButtonsList() {
        return klawiszeLK;
    }

    /***
     * Returns integer value from <1..6> generated on Liczykropka screen<br>
     * If any problems, returns -1
     */
    public int getNumber() {
        int value;
        try {
            value = Integer.parseInt(liczbaLK.getText());
            if (value > 6) return -1;
            return value;
        } catch (Exception e) {
            Reporter.log(e.getMessage());
            return -1;
        }
    }

    /***
     * Returns button that has number/label @number on it.
     */
    public WebElement getButtonWithNumber(int number) {
        String strNumber = Integer.toString(number);
        for (WebElement btn : klawiszeLK) {
            if (btn.getAttribute("wartosc").equals(strNumber)) {
                return btn;
            }
        }
        return null;
    }

    /***
     * Trying to return the green button after it becomes visible.
     * After a number of tries, returning it "as it is".
     * (initially green button is invisible, but it IS present always)
     */
    public WebElement getGreenButton() {
        final int MAX_PROBES = 10; //to prevent infinite loop
        int probe = 0;
        while (!greenButtonLK.isDisplayed()) {
            TestUtils.mySleep(200);
            probe++;
            if (probe >= MAX_PROBES) break;
        }
        return greenButtonLK;
    }
}
