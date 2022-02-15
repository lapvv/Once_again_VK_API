package trello;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import okhttp3.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specifications.Specifications;

import javax.smartcardio.Card;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;

public class TrelloAPI_TestSet {

    private final static String VK_URL = "https://api.vk.com";
    private final static String Trello_URL = "https://api.trello.com";
    public static String cardId;
    String Trello_key = System.getProperty("TRELLO_KEY");
    String Trello_token = System.getProperty("TRELLO_TOKEN");
    String Trello_secret = System.getProperty("OAuth1_secret");
    String authString = "oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;
    String authStringWithAmp = "&oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;
    String authStringWithQues = "?oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;


//    RestAssured.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultCharsetForContentType("UTF-8", "application/json")));

    @BeforeAll
    public static void init() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("app.properties"));
    }

    @Test
    public void getBoardsInfo() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
//        String method = String.format("/1/members/me/boards?key=%s&token=%s", Trello_key ,Trello_token);
        given()
                .when()
//                .header()
                .get("/1/members/me/boards?key="+Trello_key+"&token="+Trello_token)
                .then().log().body()
                .extract().jsonPath();
    }

    @Test
    public void createKanbanToolBoard(){
        String boardName = "KanbanTool";
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String boardId = given()
                .when()
                .post("/1/boards/?name="+boardName+authStringWithAmp)
                .then().log().all()
                .extract().path("id");
//        System.out.println("НОМЕР СОХРАНЕННОЙ ДОСКИ: "+boardId);
    }
// boardId = 62014c7e61a1b58a287773b2

    @Test
    public void deleteBoard(){
        String boardIdToDelete = "";
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given().when().post("/1/boards/"+boardIdToDelete+authStringWithAmp)
                .then().log().all()
                .extract().jsonPath();
    }

    @Test
    public void createListOnBoard(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String boardIdForNewList = "62014c7e61a1b58a287773b2";
        String listName = "Backlog";
        String idList= given().when().post("/1/boards/"+boardIdForNewList+"/lists?name="+listName+authStringWithAmp)
                .then().log().all()
                .extract().path("id");
    }
//idList = 6201539e495bc95acf6c9fa4

    @Test
    public void createCardOnTheListTest() throws UnsupportedEncodingException {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String idListParam = "&idList=6201539e495bc95acf6c9fa4"; //заменить на "?idList="+idList
        String cardName = "?name=Карточка для изучения API";
        cardId = given()
                .header("Accept", "application/json")
                .when()
                .post("/1/cards"+cardName+idListParam+authStringWithAmp)
                .then().log().body()
                .extract().path("id");
    }
//String cardId = "6203c130356a852cf22b8371";

    @Test
    public void createAttachmentToCard(){
        Specifications.installSpec(Specifications.requestTrelloFileSpec(Trello_URL), Specifications.responseSpec(200));
        String cardIdParam = "6203c130356a852cf22b8371";
                given()
                        .when()
                        .multiPart("file", new File("D:/SoftDev/Lanit/Trello-logo.png"),"image/png")
                        .post("/1/cards/"+cardIdParam+"/attachments"+authStringWithQues)
                        .then().log().body()
                        .extract().jsonPath();
    }
//    id = 6203cb5719b828121321f41b

    @Test
    public void updateCard() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String cardIdParam = "6203c130356a852cf22b8371";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar date2 = new GregorianCalendar();
        date2.add(date2.DAY_OF_MONTH, 1);
        given()
                .header("Accept", "application/json")
                .when()
                .param("due", dateFormat.format(date2.getTime()))
                .param("desc", "Тут будет отмечаться прогресс обучения")
                .put("/1/cards/"+cardIdParam+authStringWithQues)
                .then().log().body()
                .extract().jsonPath();
    }
    //+"?"+dateFormat.format(date2.getTime())+

    @Test
    public void createChecklist() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String cardIdParam = "6203c130356a852cf22b8371";
        String checklistId = given()
                .when()
//                .param("idCard", cardIdParam)
                .post("/1/checklists"+"?idCard="+cardIdParam+authStringWithAmp)
                .then()
                .log().body()
                .extract().path("id");
    }
    //6207d2d3e4eaee048e9b242f

    @Test
    public void createCheckItem1() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String listIdParam = "6207d2d3e4eaee048e9b242f";
        String checkItem1Id = given()
                .when()
//                .param("name", "Понять протокол HTTP")
                .post("/1/checklists/"+listIdParam+"/checkItems"+"?name=Понять протокол HTTP"+authStringWithAmp)
                .then()
                .log().body()
                .extract().path("id");
    }
    //6207ebe96dc9c8345c6f2d19

    @Test
    public void createCheckItem2() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String listIdParam = "6207d2d3e4eaee048e9b242f";
        given()
                .when()
//                .param("name", "Понять протокол HTTP")
                .post("/1/checklists/"+listIdParam+"/checkItems"+"?name=Выучить методы запросов"+authStringWithAmp)
                .then()
                .log().body()
                .extract().jsonPath();
    }
//6207ec182e24fd218e7ba75f

    @Test
    public void updateCheckItemOnACard() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
//                .auth().oauth(Trello_key, Trello_secret, Trello_token, "")
                .queryParam("state", "complete")
                .put("/1/cards/{id}/checkItem/{idCheckItem}"+authStringWithQues, "6203c130356a852cf22b8371", "6207ebe96dc9c8345c6f2d19")
                .then()
                .log().body()
                .extract().jsonPath();
    }

    @Test
    public void createDoneList() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String list2Id = given()
                .when()
//                .param("name", "Done")
//                .param("idBoard", "62014c7e61a1b58a287773b2")
                .post("/1/lists?name={name}&idBoard={id}"+authStringWithAmp, "Done", "62014c7e61a1b58a287773b2")
                .then()
                .log().body()
                .extract().path("id");
    }
    //DoneListId = 6207f8dce6fe006fca2d8bf9

    @Test
    public void updateCard2() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String cardIdParam = "6203c130356a852cf22b8371";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        GregorianCalendar date2 = new GregorianCalendar();
//        date2.add(date2.DAY_OF_MONTH, 1);
        given()
                .header("Accept", "application/json")
                .when()
//                .param("due", dateFormat.format(date2.getTime()))
//                .param("desc", "Тут будет отмечаться прогресс обучения")
                .param("idList", "6207f8dce6fe006fca2d8bf9")
                .put("/1/cards/"+cardIdParam+authStringWithQues)
                .then().log().body()
                .extract().jsonPath();
    }

    @Test
    public void updateAList() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("closed", true)
                .put("/1/lists/{id}"+authStringWithQues, "6201539e495bc95acf6c9fa4")
                .then()
                .log().body();
    }

    @Test
    public void updateCheckItemOnACardAgain() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("state", "complete")
                .put("/1/cards/{id}/checkItem/{idCheckItem}"+authStringWithQues, "6203c130356a852cf22b8371", "6207ec182e24fd218e7ba75f")
                .then()
                .log().body()
                .extract().jsonPath();
    }

    @Test
    public void addCommentToCard() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
//                .queryParam("text", "\uD83D\uDC4D")
                .queryParam("text", ":thumbsup:")
                .post("/1/cards/{id}/actions/comments"+authStringWithQues, "6203c130356a852cf22b8371")
                .then()
                .log().body();
    }
}