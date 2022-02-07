package trello;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specifications.Specifications;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TrelloAPI_TestSet {

    private final static String VK_URL = "https://api.vk.com";
    private final static String Trello_URL = "https://api.trello.com";
    String Trello_key = System.getProperty("TRELLO_KEY");
    String Trello_token = System.getProperty("TRELLO_TOKEN");

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
                .get("/1/members/me/boards?key="+Trello_key+"&token="+Trello_token)
                .then().log().body()
                .extract().jsonPath();
//                .getList(".", (Boards.class));
//        System.out.println(boardId);
//        int i=0;
    }

    @Test
    public void createKanbanTool(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String name = "newBoardFromTest";
        given()
//                .body(name)
                .when()
                .header("Authorization: OAuth oauth_consumer_key=\"{{apiKey}}\", oauth_token=\"{{apiToken}}\"", Trello_key, Trello_token)
                .post("/1/boards/?name="+name)
//                .body(createBoard.json)
                .then().log().all()
                .extract().jsonPath();
    }

    @Test
    public void createColumnInBoardTest(){
        Specifications.installSpec(Specifications.requestVKSpec(Trello_URL), Specifications.responseSpec(200));
        String boardId = "61fc0d22478dd42631d87f20";
        String listName = "newTestList";
        given()
//                .header("Accept: application/json")
                .when()
                .post("/1/boards/"+boardId+"/lists?name="+listName+"?key="+Trello_key+"&token="+Trello_token)
                .then().log().body()
                .extract().jsonPath();
    }
}
//https://api.trello.com/1/boards/{id}/lists?name={name}