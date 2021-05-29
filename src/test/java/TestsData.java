import org.testng.annotations.DataProvider;
/***
 * To enforce multiple runs of some tests.
 */
public class TestsData {

    @DataProvider(name = "GoodEmailsGenerator")
    public Object[][] getData1() {
        return new Object[][]{
                {"1", "mskib77@gmail.com"},
                {"2", "jan.kowalski@costam.com"},
        };
    }

    @DataProvider(name = "BadEmailsGenerator")
    public Object[][] getData2() {
        return new Object[][]{
                {"1", "mskib77#gmail.com"},
                {"2", "aaaa.com"},
        };
    }

    @DataProvider(name = "multiplayer")
    public Object[][] getData3() {
        return new Object[][] {
                {"przebieg", "1"},
                {"przebieg", "2"},
                {"przebieg", "3"},
        };
    }

}
