package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Thread.sleep;

public class TestUtils {

    public static final int WAIT_TIME = 7; //system-wide implicit wait
    public static final String BASE_URL = "https://autyzmsoft.pl";



    /***
     *
     * @param url given as String
     * @return true if @url points to an active address
     */
    public static boolean getLinkStatus(String url) {
        int responseCode = 0;
        final int OK =  HttpURLConnection.HTTP_OK;  //200

        try {
            URL urlObj = null;
            urlObj = new URL(url);
            HttpURLConnection huc = null;
            huc = (HttpURLConnection) urlObj.openConnection();
            huc.setRequestMethod("HEAD");
            responseCode = huc.getResponseCode();
        } catch (Exception e) {
            Reporter.log(e.getMessage());
        } finally {
            if (responseCode == OK) System.out.println(url + " - ok");
            else System.out.println(url + " - problem");
        }

        return (responseCode == OK);
    }


    public static void screenShot(WebDriver driver, String fileName) {
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        BufferedImage pict = screenshot.getImage();
        try {
            String now = java.time.LocalDateTime.now().toString();
            String type = "jpg";
            fileName += "_"+now+"."+type;
            ImageIO.write(pict, type, new File("./screenshots/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
            Reporter.log(e.getMessage());
        }
    }

    /***
     * Auxiliary; accepts style as string, number as integer; style is css style of an element
     * Expects style to include font-size in %, otherwise rises exception
     */
    public static boolean isFontSizeGreaterThan(String style, int number) {
        //parameters sanitization -----------:
        if (!style.contains("font-size:"))
            throw new IllegalArgumentException("Improper method parameter. style parameter does not include 'font-size' substring");
        String strTmp = style.split("font-size:")[1];
        if (!strTmp.contains("%"))
            throw new IllegalArgumentException("font-size is not in % (percentage) units");
        // end sanitization -----------
        strTmp = strTmp.split("%")[0];
        try {
            int wynik = Integer.parseInt(strTmp.trim());
            return wynik > number;
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException in isFontSizeGreaterThen() "+e.getMessage());
            Reporter.log(e.getMessage());
        }
        return false;
    }

    public static void mySleep(int miliseconds) {
        try {
            sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Reporter.log(e.getMessage());
        }
    }


}



