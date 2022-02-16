package APITests;

import POJO.Cards;
import POJO.Clients;
import POJO.Stamps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PositiveScenarios extends TestBase {



    @Test(priority = 0)
    public void getAllClientsCount(){

        extentLogger= report.createTest("GET: Total clients");

        Response response = given().accept(ContentType.JSON).get("/clients");

        List<Map<String, Object>> clientList = response.body().as(List.class);

        extentLogger.pass("Total clients: "+clientList.size());



    }
    @Test(priority = 1)
    public void createClient() {
        extentLogger= report.createTest("POST: Create client");

       // POST = create client with java faker
        clientName=faker.name().firstName();
        clientLastName=faker.name().lastName();

        Response response =  given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(new Clients(clientName,clientLastName))
                .when().post("/clients")
                .then().statusCode(201).extract().response().prettyPeek();

        clientId = response.path("id");
        extentLogger.info("Client name : "+clientName);
        extentLogger.info("Client lastname : "+clientLastName);
        extentLogger.info("Client id : "+clientId);
        extentLogger.info("Stamp id : "+stampId);
        extentLogger.info("Card id : "+cardId);
        extentLogger.pass("Client created");
    }

    @Test(priority = 2)
    public void createStamp(){

        extentLogger= report.createTest("POST: Create stamp");

        // POST = create stamp with java faker and Random class for amount (1-100.000)
        amount=random.nextInt(99999)+1;
        promotion=faker.food().dish();

       Response response= given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(new Stamps(amount,promotion))
                .when().post("/clients/"+clientId+"/stamps")
                .then().statusCode(201).extract().response().prettyPeek();

        stampId=response.path("id");

        String actualClientId=response.path("clientId");
        Assert.assertEquals(clientId,actualClientId);

        extentLogger.info("Client name : "+clientName);
        extentLogger.info("Client lastname : "+clientLastName);
        extentLogger.info("Client id : "+clientId);
        extentLogger.info("Amount : "+amount);
        extentLogger.info("Promotion : "+promotion);
        extentLogger.info("Stamp id : "+stampId);
        extentLogger.pass("Stamp created");


    }

    @Test(priority = 3)
    public void createCard() {

        extentLogger= report.createTest("POST: Create card");

        // POST = create card with  Random amount (1-100.000)
        amount_full_cards=random.nextInt(99)+1;
       Response response= given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(new Cards(amount_full_cards))
                .when().post("/clients/"+clientId+"/cards")
                .then().statusCode(201).extract().response().prettyPeek();

        cardId=response.path("id");

        extentLogger.info("Client name : "+clientName);
        extentLogger.info("Client lastname : "+clientLastName);
        extentLogger.info("Client id : "+clientId);
        extentLogger.info("Amount_full_cards : "+amount_full_cards);
        extentLogger.info("Card id : "+cardId);
        extentLogger.pass("Card created");
    }

    @Test(priority = 4)
    public void readClientInformation() {

        extentLogger= report.createTest("GET: Read client Information ");

        // Get
       Response response = given().
               accept(ContentType.JSON).
               get("/clients/"+clientId).
               then().
               statusCode(200).
               extract().
               response().
               prettyPeek();

        Clients client = response.body().as(Clients.class);


        Assert.assertEquals(client.getId(),clientId);
        extentLogger.pass("Client id : "+clientId);

        Assert.assertEquals(client.getName(),clientName);
        extentLogger.pass("Client name : "+clientName);

        Assert.assertEquals(client.getLastname(),clientLastName);
        extentLogger.pass("Client lastname : "+clientLastName);

        extentLogger.info("Stamp id : "+client.getStamps().get(0).getId());
        extentLogger.info("Amount : "+client.getStamps().get(0).getAmount());
        extentLogger.info("Promotion : "+client.getStamps().get(0).getPromotion());
        extentLogger.info("Card id : "+client.getCards().get(0).getId());
        extentLogger.info("Amount_full_cards : "+client.getCards().get(0).getAmount_full_cards());
        extentLogger.pass("Client information read");


    }


    @Test(priority = 5)
    public void readClientStampInformation() {

        extentLogger= report.createTest("GET: Read stamp information ");

        // Get
        Response response = given().
                accept(ContentType.JSON).
                get("/clients/"+clientId+"/stamps/"+stampId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

        Stamps stamp = response.body().as(Stamps.class);

        extentLogger.info("Stamp id :"+stampId);

        Assert.assertEquals(stamp.getPromotion(),promotion);
        extentLogger.pass("Promotion : "+promotion);

        Assert.assertEquals(stamp.getAmount(),amount);
        extentLogger.pass("Amount : "+amount);

        extentLogger.pass("Stamp information read");

    }

    @Test(priority = 6)
    public void readClientCardInformation() {

        extentLogger= report.createTest("GET: Read card information ");
        // Get
        Response response = given().
                accept(ContentType.JSON).
                get("/clients/"+clientId+"/cards/"+cardId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

       Cards card = response.body().as(Cards.class);


        extentLogger.info("Amount_full_cards : "+amount_full_cards);
        Assert.assertEquals(card.getAmount_full_cards(),amount_full_cards);

        extentLogger.pass("Card information read");
    }


    @Test(priority = 7)
    public void updateClientCardInformation() {
        // put
        extentLogger= report.createTest("PUT: Update card information ");
        extentLogger.info("Card id : "+cardId);
        extentLogger.info("Last amount_full_cards : "+amount_full_cards);

        amount_full_cards=random.nextInt(99)+1;

        Map<String,Integer> newCard=new HashMap<>();
        newCard.put("amount_full_cards",amount_full_cards);


        Response response = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                body(newCard).
               put("/clients/"+clientId+"/cards/"+cardId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

        Cards card = response.body().as(Cards.class);
        extentLogger.info("Card id : "+cardId);
        extentLogger.info("New amount_full_cards : "+amount_full_cards);
        Assert.assertEquals(card.getAmount_full_cards(),amount_full_cards);

        extentLogger.pass("Card information updated");

    }

    @Test(priority = 8)
    public void updateClientStampInformation() {
        extentLogger= report.createTest("PUT: Update Stamp information ");
        extentLogger.info("Stamp id : "+stampId);
        extentLogger.info("Last amount : "+amount);
        extentLogger.info("Last promotion : "+promotion);

        // put
        amount=random.nextInt(99999)+1;
        promotion=faker.food().fruit();


        Map<String,Object> newStamps=new HashMap<>();
        newStamps.put("amount",amount);
        newStamps.put("promotion",promotion);


        Response response = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                body(newStamps).
                put("/clients/"+clientId+"/stamps/"+stampId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

        Stamps stamp = response.body().as(Stamps.class);

        Assert.assertEquals(stamp.getId(),stampId);
        extentLogger.pass("Stamp id : "+stampId);


        Assert.assertEquals(stamp.getAmount(),amount);
        extentLogger.pass("New amount : "+amount);


        Assert.assertEquals(stamp.getPromotion(),promotion);
        extentLogger.pass("New promotion : "+promotion);


        extentLogger.pass("Stamp information updated");
    }

    @Test(priority = 9)
    public void updateClientInformation() {
        extentLogger= report.createTest("PUT: Update client information ");
        extentLogger.info("Client id : "+clientId);
        extentLogger.info("Last client name : "+clientName);
        extentLogger.info("Last client Lastname : "+clientLastName);
        // put
        clientName=faker.name().firstName();
        clientLastName=faker.name().lastName();

        Response response = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                body(new Clients(clientName,clientLastName)).
                put("/clients/"+clientId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

        extentLogger.info("Client id : "+clientId);
        Clients client = response.body().as(Clients.class);

        Assert.assertEquals(client.getName(),clientName);
        extentLogger.pass("New client name : "+clientName);


        Assert.assertEquals(client.getLastname(),clientLastName);
        extentLogger.pass("New client Lastname : "+clientLastName);

        extentLogger.pass("Client information updated");

    }
    @Test(priority = 10)
    public void deleteClientCard() {

        extentLogger= report.createTest("DELETE: Delete card information ");
        // delete
      given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON).
              delete("/clients/"+clientId+"/cards/"+cardId).
                then().statusCode(200).extract().
                response().prettyPeek();
        //get
        String result= given().
                accept(ContentType.JSON).and().
                contentType(ContentType.JSON).
                get("/clients/"+clientId+"/cards/"+cardId).prettyPrint();

        extentLogger.info("Card check :"+result);
        extentLogger.pass("Card information deleted");
    }
    @Test(priority = 11)
    public void deleteClientStamp() {
        extentLogger= report.createTest("DELETE: Delete stamp information ");
        // delete
        Response response = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                delete("/clients/"+clientId+"/stamps/"+stampId).
                then().
                statusCode(200).
                extract().
                response().
                prettyPeek();

        //get
        String result = given().
                accept(ContentType.JSON).
                and().
                contentType(ContentType.JSON).
                get("/clients/"+clientId+"/stamps/"+stampId).prettyPrint();

        extentLogger.info("Stamp check :"+result);
        extentLogger.pass("Stamp information deleted");
    }


    @Test(priority = 12)
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
