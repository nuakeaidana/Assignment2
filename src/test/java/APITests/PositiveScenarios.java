package APITests;

import POJO.Card;
import POJO.Efteling;
import POJO.Stamp;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PositiveScenarios extends TestBase {
    String ID;

    @Test(priority = 1)
    public void post() {
        Efteling personTicket = new Efteling();
        Card cards = new Card();
        Stamp stamps = new Stamp();
        personTicket.setName("Mike");
        personTicket.setLastname("Smith");
        cards.setAmountFullCards(251);
        stamps.setAmount(4);
        stamps.setPromotion("Efteling");
        List<Card> card = new ArrayList<>();
        card.add(cards);
        List<Stamp> stamp = new ArrayList<>();
        stamp.add(stamps);
        personTicket.setCards(card);
        personTicket.setStamps(stamp);

        given().accept(ContentType.JSON).body(personTicket).post().then()
                .assertThat().contentType(ContentType.JSON).statusCode(201);
    }

    @Test(priority = 2)
    public void get() {
        Response response = given().accept(ContentType.JSON).when()
                .get("/clients").then().statusCode(200).extract().response();
        List<Map<String,Object>> responseList = response.body().as(List.class);
        System.out.println(responseList);
        assertEquals(responseList.get(52).get("name"),"Mike");
    }







}
