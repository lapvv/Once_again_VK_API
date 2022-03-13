package trello;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import okhttp3.MediaType;
import org.junit.jupiter.api.*;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
//    String authStringWithAmp = "&oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;
//    String authStringWithQues = "?oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;


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

    private void updateCheckItemOnACardComplete(String cardId, String checkItemId) {
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
    @Order(1)
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
    @Order(2)
    public void createKanbanToolBoard(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        boardId = given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .queryParam("name", boardName)
                .post("/1/boards/")
                .then().log().all()
                .extract().path("id");
//        System.out.println("НОМЕР СОХРАНЕННОЙ ДОСКИ: "+boardId);
    }
//        boardId = "6228f9e5e473c5095d6c242c";

    @Test
    @Order(3)
    public void create1ListOnBoard(){
        createListOnBoard(list1Name, boardId, listId);
    }
//idList = "6228f9e6cc43002d4a67b1f6";

    @Test
    @Order(4)
    public void createCardOnTheListTest() {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        cardId = given()
//                .header("Accept", "application/json")
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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

/*    @Test
    public void createChecklistOld() {
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

 */

    @Test
    @Order(8)
    public void createCheckItem1() {
        createCheckItem(task1, checkItem1Id);
    }
//checkItem1Id = "6207ebe96dc9c8345c6f2d19";

    @Test
    @Order(9)
    public void createCheckItem2() {
        createCheckItem(task2, checkItem2Id);
    }
//checkItem2Id = "6207ec182e24fd218e7ba75f";

    @Test
    @Order(10)
    public void update1CheckItemOnACard(){
        updateCheckItemOnACardComplete(cardId, checkItem1Id);
    }

    @Test
    @Order(11)
    public void createDoneList() {
        createListOnBoard(list2Name, boardId, doneListId);
    }
    //doneListId = 6207f8dce6fe006fca2d8bf9

    @Test
    @Order(12)
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
    @Order(13)
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
    @Order(14)
    public void update2CheckItemOnACard(){
        updateCheckItemOnACardComplete(cardId, checkItem2Id);
    }

    @Test
    @Order(15)
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

    @Test
    @Order(16)
    public void deleteBoard(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given()
                .when()
                .queryParam("key", Trello_key)
                .queryParam("token", Trello_token)
                .delete("/1/boards/"+boardId)
                .then().log().body()
                .extract().jsonPath();
    }

}