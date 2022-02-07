import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
//import org.junit.Assert;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static groovyjarjarantlr4.v4.runtime.misc.Utils.readFile;
import static io.restassured.RestAssured.given;
//import static java.util.Collections.get;

public class VK_Test {

    private static final String URL= "https://reqres.in/";

//  System.getProperties().load(ClassLoader.getSystemResourceAsStream("my.properties"));

    @Test
    public void test1test(){

        List<UserInfo> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserInfo.class);
//        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
    }

    private static final String URL_VK = "https://api.vk.com";
//    private static final String APP_ID = Config.get("app_id");
//    private static final String OWNER_ID = Config.get("owner_id");
//    private static final String TOKEN = Config.get("access_token");
//    private static final String API_VERSION = Config.get("api_version");


//    @Test
//    public static String postMessageToTheWall(String message){
//        String request = String.format("%s?owner_id=%s&message=%s&access_token=%s&v=%s", "wall.post", OWNER_ID, message, TOKEN, API_VERSION);
//    }
//    https://api.vk.com/method/users.get?user_id=210700286&v=5.131


    @Test
    public void getProfileInfo(){
//        String jsonString = readFile("/to-java-collection/books.json");
//        ObjectMapper objectMapper = null;
//        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, UserInfo2.class);
//        ObjectMapper mapper = new ObjectMapper();
//
//        JsonNode user1 = given()
//                .when()
//                .get(URL_VK+"/method/users.get?user_id=210700286&v=5.131")
//                .then().extract().as(UserInfo2.class);

//        https://api.vk.com/method/METHOD?PARAMS&access_token=TOKEN&v=V

        UserInfo2 user1 = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_VK+"/method/account.getProfileInfo&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
                .then()
                .extract().body().jsonPath().getObject("", UserInfo2.class);
    }

    @Test
    public void getProfileInfo2(){
//        JsonPath user1 = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .post(URL_VK+"/method/account.getProfileInfo&access_token=39d20e7e297f11f74b02323a23a688fed33c90e755f0e87e058838fa4ee16cf0dfb90600d3e4590302fb6&v=5.131")
//                .then()
//                .extract().body().jsonPath();
//        List<Map<String, Object>> products = get("/products").as(new TypeRef<List<Map<String, Object>>>() {});
    }

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
