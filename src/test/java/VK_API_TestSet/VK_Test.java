package VK_API_TestSet;

import com.sun.deploy.config.Config;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
//import org.junit.Assert;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specifications.Specifications;

import java.io.IOException;
import java.util.*;

import static groovyjarjarantlr4.v4.runtime.misc.Utils.readFile;
import static io.restassured.RestAssured.given;
//import static java.util.Collections.get;

public class VK_Test {

    private static final String URL= "https://reqres.in/";
    private static final String URL_VK = "https://api.vk.com";
    String VK_APP_ID = System.getProperty("VK_APP_ID");
    String VK_TOKEN = System.getProperty("VK_TOKEN");
    String VK_version = System.getProperty("VK_API_VERSION");

//    @Test
//    public void ReqresTest(){
//
//        List<UserInfo> users = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .get(URL+"api/users?page=2")
//                .then().log().all()
//                .extract().body().jsonPath().getList("data", UserInfo.class);
////        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
//    }

//    https://api.vk.com/method/METHOD?PARAMS&access_token=TOKEN&v=V
    @BeforeAll
    public static void init() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("app.properties"));
    }

    @Test //DONE
    public void getProfileInfo(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
//        String jsonString = readFile("/to-java-collection/books.json");
//        ObjectMapper objectMapper = null;
//        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, VK_API_TestSet.UserInfo2.class);
//        ObjectMapper mapper = new ObjectMapper();
        given()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .when()
//                .pathParam("v", VK_version)
                .get("/method/users.get?v="+VK_version)
                .then()
                .log().body()
                .extract().jsonPath();
    }

    @Test //DONE
    public void getProfileInfo2(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .post("/method/account.getProfileInfo?v="+VK_version)
                .then()
                .log().body()
                .extract().jsonPath();
    }

        /*UserInfo2 user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/account.getProfileInfo&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath().getObject("", UserInfo2.class);*/

    //    @Test
//    public static String postMessageToTheWall(String message){
//        String request = String.format("%s?owner_id=%s&message=%s&access_token=%s&v=%s", "wall.post", OWNER_ID, message, TOKEN, API_VERSION);
//    }
//    https://api.vk.com/method/users.get?user_id=210700286&v=5.131



    @Test
    public void docLoad(){
        JsonPath user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/docs.getTypes&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath();
    }

    @Test
    public void wallPost(){
        JsonPath user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/wall.post?user_id=13855514&message=This is a test message. I am learning API VK&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath();
    }

    @Test
    public void wallPostWithSpaces(){
        JsonPath user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/wall.post?user_id=13855514&message=This%20is%20a%20test%20message.%20I%20am%20learning%20API%20VK&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath();
    }

    @Test
    public void wallEdit(){
        JsonPath user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/wall.edit?user_id=13855514&post_id=604&message=This+is+a+test+message.+I+am+learning&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath();
    }

    @Test
    public void wallDelete(){
        JsonPath user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/wall.delete?user_id=13855514&post_id=606&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath();
    }
}
