package utilities;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

// I put a logic that should apply to every scenario
public abstract class TestBase {

    @BeforeClass
    //baseURI and basePath to set up scenarios
    public static void setUp () {
        baseURI = ConfigurationReader.get("baseURI");
    }
    @AfterClass
    //reset method to reset scenarios after the execution
    public static void tearDown () {
        reset();
    }

}

