package APITests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestBase;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class NegativeScenarios extends TestBase {

    @Test(priority = 13)
    public void createClientWithoutName() {
        extentLogger= report.createTest("POST: Create client without name ");

        // POST = create client lastname with java faker
        clientLastName=faker.name().lastName();

        Map<String,String> client=new HashMap<>();
        client.put("lastname",clientLastName);


        Response response =  given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(client)
                .when().post("/clients")
                .prettyPeek();

        clientId = response.path("id");
        clientName=response.path("name");

        Assert.assertTrue(clientName.equals(null));
        extentLogger.pass("Client name must be null");

        //system automatically generates name when creating anonymous client
    }

    @Test(priority = 14)
    public void createStampWithoutPromotion() {

        extentLogger= report.createTest("POST: Create stamp without promotion");
        amount=random.nextInt(99999)+1;

        Map<String,Object> stamp=new HashMap<>();
        stamp.put("amount",amount);
        // POST = create stamp with Random class for amount (1-100.000)


        Response response= given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(stamp)
                .when().post("/clients/"+clientId+"/stamps").prettyPeek();

        stampId=response.path("id");
        amount=response.path("amount");
        promotion=response.path("promotion");

        extentLogger.info("Client name : "+clientName);
        extentLogger.info("Client lastname : "+clientLastName);
        extentLogger.info("Client id : "+clientId);
        extentLogger.info("Amount : "+amount);
        extentLogger.info("Promotion : "+promotion);
        extentLogger.info("Stamp id : "+stampId);
        extentLogger.info("Promotion must be null");
        Assert.assertTrue(promotion.equals(null));
        extentLogger.pass("PASS");
    }

    @Test(priority = 15)
    public void createCardWithStringAmount() {

//      POST = create card with  Random amount (1-100.000)
        extentLogger= report.createTest("POST: Create card with string amount_full_cards");

        Map<String,Object> card=new HashMap<>();
        card.put("amount_full_cards","amount");



        Response response= given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(card)
                .when().post("/clients/"+clientId+"/cards").prettyPeek();

        cardId=response.path("id");



        String newAmount_full_cards=response.path("amount_full_cards");

        extentLogger.info("Client name : "+clientName);
        extentLogger.info("Client lastname : "+clientLastName);
        extentLogger.info("Client id : "+clientId);
        extentLogger.info("Amount_full_cards : "+newAmount_full_cards);
        extentLogger.info("Card id : "+cardId);
        try {
            amount_full_cards=Integer.parseInt(newAmount_full_cards);
            Assert.assertTrue(true);
            extentLogger.pass("amount_full_cards is digit");
        }catch (Exception e){
            extentLogger.fail("Amount_full_cards is not Integer");
            Assert.assertTrue(false);
        }



    }


    @Test(priority = 16)
    public void deleteClient() {

        extentLogger= report.createTest("DELETE: Delete client ");
        // delete

        Response response = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                delete("/clients/"+clientId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

        String result = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                get("/clients/"+clientId).prettyPrint();

        extentLogger.info("Client check : "+result);
        extentLogger.pass("Client deleted");

    }


}
