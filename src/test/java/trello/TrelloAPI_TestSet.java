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

//    private final static String VK_URL = "https://api.vk.com";
    private final static String Trello_URL = "https://api.trello.com";
    final static String boardName = "KanbanTool";
    final static String list1Name = "Backlog";
    final static String list2Name = "Done";
    final static String cardName = "Карточка для изучения API";
    final static String task1 = "Понять протокол HTTP";
    final static String task2 = "Выучить методы запросов";
    static String boardId;
    static String listId;
    static String doneListId;
    static String cardId;
    static String checklistId;
    static String checkItem1Id;
    static String checkItem2Id;
    String Trello_key = System.getProperty("TRELLO_KEY");
    String Trello_token = System.getProperty("TRELLO_TOKEN");
//    String Trello_secret = System.getProperty("OAuth1_secret");
//    String authString = "oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;
    String authStringWithAmp = "&oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;
    String authStringWithQues = "?oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;


//    RestAssured.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultCharsetForContentType("UTF-8", "application/json")));

    private void createListOnBoard(String listName, String boardId, String listId){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        listId = given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .queryParam("name", listName)
                .post("/1/boards/"+boardId+"/lists")
                .then().log().body()
                .extract().path("id");
    }

    private void createCheckItem(String task, String checkItemId){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        checkItemId = given()
                .when()
                .queryParam("name", task)
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .post("/1/checklists/"+checklistId+"/checkItems")
                .then()
                .log().body()
                .extract().path("id");
    }

    private void updateCheckItemOnACard(String cardId, String checkItemId) {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .queryParam("state", "complete")
                .put("/1/cards/{id}/checkItem/{idCheckItem}", cardId, checkItemId)
                .then()
                .log().body()
                .extract().jsonPath();
    }

    @BeforeAll
    public static void init() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("app.properties"));
    }

    @Test
    public void getBoardsInfo() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .get("/1/members/me/boards")
                .then().log().body()
                .extract().jsonPath();
    }

    @Test
    public void createKanbanToolBoard(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        boardId = given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .post("/1/boards/?name="+boardName)
                .then().log().all()
                .extract().path("id");
//        System.out.println("НОМЕР СОХРАНЕННОЙ ДОСКИ: "+boardId);
    }
//        boardId = "62014c7e61a1b58a287773b2";
//?name="+listName

    @Test
    public void create1ListOnBoard(){
        createListOnBoard(list1Name, boardId, listId);
    }
//idList = "6201539e495bc95acf6c9fa4";

    @Test
    public void createCardOnTheListTest() throws UnsupportedEncodingException {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
//        String idListParam = "&idList=6201539e495bc95acf6c9fa4"; //заменить на "?idList="+idList
        cardId = given()
                .header("Accept", "application/json")
                .when()
                .queryParam("name", cardName)
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .queryParam("idList", listId)
                .post("/1/cards")
                .then().log().body()
                .extract().path("id");
    }
//String cardId = "6203c130356a852cf22b8371";

    @Test
    public void createAttachmentToCard(){
        Specifications.installSpec(Specifications.requestTrelloFileSpec(Trello_URL), Specifications.responseSpec(200));
//        String cardIdParam = "6203c130356a852cf22b8371";
                given()
                        .when()
                        .queryParam("key", Trello_key)
                        .queryParam("token", Trello_token)
                        .multiPart("file", new File("D:/SoftDev/Lanit/Trello-logo.png"),"image/png")
                        .post("/1/cards/"+cardId+"/attachments")
                        .then().log().body()
                        .extract().jsonPath();
    }
//    id = 6203cb5719b828121321f41b

    @Test
    public void updateCard() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
//        String cardIdParam = "6203c130356a852cf22b8371";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar date2 = new GregorianCalendar();
        date2.add(date2.DAY_OF_MONTH, 1);
        given()
                .header("Accept", "application/json")
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .param("due", dateFormat.format(date2.getTime()))
                .param("desc", "Тут будет отмечаться прогресс обучения")
                .put("/1/cards/"+cardId)
                .then().log().body()
                .extract().jsonPath();
    }

    @Test
    public void createChecklist() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
//        String cardIdParam = "6203c130356a852cf22b8371";
        checklistId = given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .queryParam("idCard", cardId)
                .post("/1/checklists")
                .then()
                .log().body()
                .extract().path("id");
    }
    //checklistId = "6207d2d3e4eaee048e9b242f";

    @Test
    public void createCheckItem1() {
        createCheckItem(task1, checkItem1Id);
    }
//checkItem1Id = "6207ebe96dc9c8345c6f2d19";

    @Test
    public void createCheckItem2() {
        createCheckItem(task2, checkItem2Id);
    }
//checkItem2Id = "6207ec182e24fd218e7ba75f";

    @Test
    public void update1CheckItemOnACard(){
        updateCheckItemOnACard(cardId, checkItem1Id);
    }

    @Test
    public void createDoneList() {
        createListOnBoard(list2Name, boardId, doneListId);
    }
    //doneListId = 6207f8dce6fe006fca2d8bf9

    @Test
    public void updateCard2() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .header("Accept", "application/json")
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .param("idList", doneListId)
                .put("/1/cards/"+cardId)
                .then().log().body()
                .extract().jsonPath();
    }

    @Test
    public void updateAList() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("closed", true)
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .put("/1/lists/{id}", listId)
                .then()
                .log().body();
    }

    @Test
    public void update2CheckItemOnACard(){
        updateCheckItemOnACard(cardId, checkItem2Id);
    }

    @Test
    public void addCommentToCard() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
//                .queryParam("text", "\uD83D\uDC4D")
                .queryParam("text", ":thumbsup:")
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .post("/1/cards/{id}/actions/comments", cardId)
                .then()
                .log().body();
    }
/*
    @Test
    public void deleteBoard(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .post("/1/boards/"+boardId)
                .then().log().body()
                .extract().jsonPath();
    }
    */
}