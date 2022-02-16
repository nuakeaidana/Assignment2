package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.github.javafaker.Faker;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import utilities.ConfigurationReader;

import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.reset;

// I put a logic that should apply to every scenario
public abstract class TestBase {

    protected static ExtentReports report;
    //this class is used to create HTML report file
    protected static ExtentHtmlReporter htmlReporter;
    //this will  define a test, enables adding logs, authors, test steps
    protected static ExtentTest extentLogger;

    protected static Faker faker=new Faker();
    protected static Random random=new Random();
    protected static String clientName;
    protected static String clientLastName;
    protected static String clientId;
    protected static int amount;
    protected static String promotion;
    protected static String stampId;
    protected static int amount_full_cards;
    protected static String cardId;

    @BeforeTest
    //baseURI and basePath to set up scenarios
    public static void setUp () {
        baseURI = ConfigurationReader.get("baseURI");

        //initialize the class
        report = new ExtentReports();

        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/Test-Results/report.html";

        //initialize the html reporter with the report path
        htmlReporter = new ExtentHtmlReporter(path);

        //attach the html report to report object
        report.attachReporter(htmlReporter);

        //title in report
        htmlReporter.config().setReportName("Aidana Boronbaeva API Test");

        //set environment information
        report.setSystemInfo("Test Type","API");
        report.setSystemInfo("URI", ConfigurationReader.get("baseURI"));
        report.setSystemInfo("Endpoints","/clients, /stamps, /cards");
    }
@BeforeClass
public static void informationReset () {
     clientName=null;
     clientLastName=null;
     clientId=null;
     amount=0;
     promotion=null;
     stampId=null;
     amount_full_cards=0;
     cardId=null;

}

    //ITestResult class describes the result of a test in TestNG
    @AfterMethod
    public static void tearDown(ITestResult result)  {
        //if test fails
        if(result.getStatus()==ITestResult.FAILURE){
            //record the name of failed test case
            extentLogger.fail(result.getName());

            //capture the exception and put inside the report
            extentLogger.fail(result.getThrowable());

        }
    }
    @AfterTest
    //reset method to reset scenarios after the execution
    public static void tearDownClass () {
        report.flush();
        reset();
    }

}

