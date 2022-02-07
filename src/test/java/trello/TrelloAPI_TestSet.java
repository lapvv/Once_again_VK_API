package trello;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import okhttp3.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specifications.Specifications;

import javax.smartcardio.Card;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TrelloAPI_TestSet {

    private final static String VK_URL = "https://api.vk.com";
    private final static String Trello_URL = "https://api.trello.com";
    String Trello_key = System.getProperty("TRELLO_KEY");
    String Trello_token = System.getProperty("TRELLO_TOKEN");
    String authString = "&oauth_consumer_key="+Trello_key+"&oauth_token="+Trello_token;

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
                .post("/1/boards/?name="+boardName+authString)
                .then().log().all()
                .extract().path("id");
//        System.out.println("НОМЕР СОХРАНЕННОЙ ДОСКИ: "+boardId);
    }
// boardId = 62014c7e61a1b58a287773b2

    @Test
    public void deleteBoard(){
        String boardIdToDelete = "";
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        given().when().post("/1/boards/"+boardIdToDelete+authString)
                .then().log().all()
                .extract().jsonPath();
    }

    @Test
    public void createListOnBoard(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String boardIdForNewList = "62014c7e61a1b58a287773b2";
        String ListName = "Backlog";
        String idList= given().when().post("/1/boards/"+boardIdForNewList+"/lists?name="+ListName+authString)
                .then().log().all()
                .extract().path("id");
    }
//idList = 6201539e495bc95acf6c9fa4
    // ------------------------------------------------
//MediaType
//    MediaType mediaType = MediaType.parse("text/plain");
//    RequestBody body = RequestBody.create(mediaType, "");
    @Test
    public void createCardOnTheListTest() throws UnsupportedEncodingException {
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String idListParam = "?idList=6201539e495bc95acf6c9fa4"; //заменить на "?idList="+idList
//        String CardName = URLDecoder.decode("?name=Карточка_для_изучения_API","UTF-8");
        String CardName = "?name=Карточка для изучения API";
        String cardId = given()
//        .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultCharsetForContentType("UTF-8", "application/json")))
                .contentType(ContentType.JSON)
//                .param("name", "Карточка для изучения API")
                .header("Accept", "application/json")
                .when()
//                .body(CardName)
                .post("/1/cards"+idListParam+CardName+authString)
                .then().log().body()
                .extract().path("id");
    }

//cardId = 62015fbc58e06e1fab233d26
//

}