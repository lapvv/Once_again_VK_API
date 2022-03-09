package VK_API_TestSet;

import POJOs.DocLoad;
import POJOs.PictureLoad;
import com.sun.deploy.config.Config;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
//import org.junit.Assert;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specifications.Specifications;

import java.io.File;
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
                .queryParam("v", VK_version)
                .get("/method/users.get")
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

    @Test //DONE
    public void setProfileInfo(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("v", VK_version)
                .queryParam("city_id", 56)
                .post("/method/account.saveProfileInfo")
                .then()
                .log().body()
                .extract().jsonPath();
    }

    @Test
    public void setAvatar_getServer(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        String picServerURL = given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("v", VK_version)
                .post("/method/photos.getOwnerPhotoUploadServer")
                .then()
                .log().body()
                .extract().path("upload_url");
//        System.out.println(serverURL);
    }

    String picServerURL = "https://pu.vk.com/c535232/ss2262/upload.php?_query=eyJhY3QiOiJvd25lcl9waG90byIsInNhdmUiOjEsImFwaV93cmFwIjp7InNlcnZlciI6OTk5LCJwaG90byI6IntyZXN1bHR9IiwibWlkIjoxMzg1NTUxNCwiaGFzaCI6Ijc0NDhmOTNmNDA1ZGZkNWM0NDRmNWQ0YWFiYjg0OTQyIiwibWVzc2FnZV9jb2RlIjoyLCJwcm9maWxlX2FpZCI6LTZ9LCJvaWQiOjEzODU1NTE0LCJtaWQiOjEzODU1NTE0LCJzZXJ2ZXIiOjUzNTIzMiwiX29yaWdpbiI6Imh0dHBzOlwvXC9hcGkudmsuY29tIiwiX3NpZyI6IjBmYmE1MDhhNjJjZTY0NWY5ZDYyYzM1YWFlMTg0YTkwIn0";
    PictureLoad serverCredentials = new PictureLoad();

    @Test
    public void setAvatar_uploadPicture(){
        Specifications.installSpec(Specifications.requestTrelloFileSpec(URL_VK), Specifications.responseSpec(200));
        serverCredentials = given()
                .when()
                .multiPart("photo", new File("D:/SoftDev/Lanit/API-Testing-3.jpg"), "image/jpg")
                .post(picServerURL)
                .then().log().body()
                .extract().as(PictureLoad.class);
        System.out.println(serverCredentials.getServer());
        System.out.println(serverCredentials.getPhoto());
        System.out.println(serverCredentials.getHash());
    }

    Integer server = 999;
    String hash = "7448f93f405dfd5c444f5d4aabb84942";
    String photo = "eyJvaWQiOjEzODU1NTE0LCJwaG90byI6eyJtYXJrZXJzX3Jlc3RhcnRlZCI6dHJ1ZSwicGhvdG8iOiJiNzFkNWUxZWQ4OngiLCJzaXplcyI6W10sImxhdGl0dWRlIjowLCJsb25naXR1ZGUiOjAsImtpZCI6IjdmYzkxYjc2NTQyMTM2NzRhYTJkMTA5MjM0ODM3MDJkIiwic2l6ZXMyIjpbWyJzIiwiYjAwNzJjM2E2NDk5ZTAyZDJlNDgwMWY5ODZjZDBmMzBkNzBjNjJkMjZlNjc1ZDllYTY1YTc1YTkiLCIyODI0NjQ1MzM1NDY2NTc5NzUiLDc1LDYzXSxbIm0iLCI4MTA5OTZhNDMyZjkzODE1NjNmYWNmODNlODg3YjhhMjQyY2UzNzI4MjBiYzAyMDg5MWE4YjYzMiIsIi00Mzc2ODgwODIyMjcyNTA0NzYxIiwxMzAsMTA5XSxbIngiLCIxNTNmY2M2MjBjMWQzZTQwNWIyNThjMDlmOTI1ZWZiNjBkYmIwM2I5NzMzYThlOWYzY2M3ZTE5YyIsIi04NzQ5ODcyNDQ0NjM3MDg3OTYzIiwzMDQsMjU0XSxbIm8iLCI4MTA5OTZhNDMyZjkzODE1NjNmYWNmODNlODg3YjhhMjQyY2UzNzI4MjBiYzAyMDg5MWE4YjYzMiIsIi00Mzc2ODgwODIyMjcyNTA0NzYxIiwxMzAsMTA5XSxbInAiLCIxMmM3NTQwNDM5YmMxNmQxZWNlN2FjZWRjOTFlMjA2MDE2NDQ3ZDM0MTFjMmM4NTNlM2YxZTA0MyIsIjE1ODI3Njc2NzQwMjExNDg4MTMiLDIwMCwxNjddLFsicSIsImU0NWY2MmQ5ZWQ3MmI5ODEzNzQ5ZWFlM2Q2ZDNmZWU0MmVhYTkzMDJlZGQ5YmM2NTE2YjkyYWVmIiwiLTgyNDg0Mzc2MDQxNzc0MzEzMDUiLDMwNCwyNTRdLFsiciIsImU0NWY2MmQ5ZWQ3MmI5ODEzNzQ5ZWFlM2Q2ZDNmZWU0MmVhYTkzMDJlZGQ5YmM2NTE2YjkyYWVmIiwiLTgyNDg0Mzc2MDQxNzc0MzEzMDUiLDMwNCwyNTRdXSwidXJscyI6W10sInVybHMyIjpbInNBY3NPbVNaNEMwdVNBSDVoczBQTU5jTVl0SnVaMTJlcGxwMXFRXC90LWl4Sl8tRDZ3TS5qcGciLCJnUW1XcERMNU9CVmotcy1ENkllNG9rTE9OeWdndkFJSWthaTJNZ1wvUjB3NjlCUXlRc00uanBnIiwiRlRfTVlnd2RQa0JiSll3Si1TWHZ0ZzI3QTdsek9vNmZQTWZobkFcL0pYZHQwMTQxa29ZLmpwZyIsImdRbVdwREw1T0JWai1zLUQ2SWU0b2tMT055Z2d2QUlJa2FpMk1nXC9SMHc2OUJReVFzTS5qcGciLCJFc2RVQkRtOEZ0SHM1Nnp0eVI0Z1lCWkVmVFFSd3NoVDRfSGdRd1wvalhnQVROQWU5eFUuanBnIiwiNUY5aTJlMXl1WUUzU2VyajF0UC01QzZxa3dMdDJieGxGcmtxN3dcLzk3UUhlN09waDQwLmpwZyIsIjVGOWkyZTF5dVlFM1NlcmoxdFAtNUM2cWt3THQyYnhsRnJrcTd3XC85N1FIZTdPcGg0MC5qcGciXX0sInNxdWFyZSI6IiIsImRhdGEiOlsie1wicGhvdG9cIjpcIjdiOWY4ZTUxOGZ4XCIsXCJzaXplc1wiOltdLFwic2l6ZXMyXCI6W1tcIm1heFwiLFwiMTUzZmNjNjIwYzFkM2U0MDViMjU4YzA5ZjkyNWVmYjYwZGJiMDNiOTczM2E4ZTlmM2NjN2UxOWNcIixcIi04NzQ5ODcyNDQ0NjM3MDg3OTYzXCIsMzA0LDI1NF0sW1wib1wiLFwiMjAyYmUwYjAxZDQ2OTUzY2EyZmY0ZTM0YTFkYjUzNjYxNmRmMGE3NzdlOTc4OTg4ZjA3MmQyMThcIixcIjM5NTUyMTQxNjk3MzM2ODQzNjRcIiwyNTQsMjU0XSxbXCJhXCIsXCJjZWY1OWE5MmVhZGUwYmRiYmFmYTdkNjQ1ODIwOGJhZGEyNmJkNjZkZDliZmUzOGQyMGZmZTBiY1wiLFwiMjI4NjgzODA2MzE0NjYyODg3N1wiLDIwMCwyMDBdLFtcImNcIixcIjE3NGYzYzAxNTFhYTRkYWE3MDFiMWU5NDZjYWIxYzk1MmI2YzkzMjcwNzFmNjk2NWNjZjM1MjhmXCIsXCItNzkwNzg3NTk0ODA3OTI5NjI3OFwiLDIwMCwyMDBdLFtcImRcIixcImQ3NzY5NmUxMWI1M2Q3ZjI2YjQzNTAxODM2MTYwYzc4ZWQ0MmMzYTcyYWRlZTVhYmEzYjc5MjNlXCIsXCIyMjk4MjA2OTQ0MTA3OTM5Mjc0XCIsMTAwLDEwMF0sW1wiZVwiLFwiYjBlMDBmY2Q0MDBhN2QyYWEwOTE5ZmIxZDk4M2I2NWM2M2Q1YjkwZmQwZmYwOWQyOTZmZWE3NmZcIixcIjI3Nzg0MjAyMzMzNDQ0NTA3M1wiLDUwLDUwXV0sXCJ1cmxzXCI6W10sXCJ1cmxzMlwiOltcIkZUX01ZZ3dkUGtCYkpZd0otU1h2dGcyN0E3bHpPbzZmUE1maG5BXC9KWGR0MDE0MWtvWS5qcGdcIixcIklDdmdzQjFHbFR5aV8wNDBvZHRUWmhiZkNuZC1sNG1JOEhMU0dBXC9qRnlSN1ZTLTR6WS5qcGdcIixcInp2V2FrdXJlQzl1Ni1uMWtXQ0NMcmFKcjFtM1p2LU9OSVBfZ3ZBXC9EWGV5TEJwN3ZCOC5qcGdcIixcIkYwODhBVkdxVGFwd0d4NlViS3NjbFN0c2t5Y0hIMmxselBOU2p3XC82dERnRXJtVVFaSS5qcGdcIixcIjEzYVc0UnRUMV9KclExQVlOaFlNZU8xQ3c2Y3EzdVdybzdlU1BnXC95c1hqQ3dyZjVCOC5qcGdcIixcInNPQVB6VUFLZlNxZ2taLXgyWU8yWEdQVnVRX1Ffd25TbHY2bmJ3XC9FWUNHSU5rWDJ3TS5qcGdcIl0sXCJtYXJrZXJzX3Jlc3RhcnRlZFwiOnRydWV9IiwiMjUsMCwyNTQsMjU0LDI1LDI1LDIwMyJdLCJid2FjdCI6Im93bmVyX3Bob3RvIiwic2VydmVyIjo1MzUyMzIsIm1pZCI6MTM4NTU1MTQsIl9zaWciOiI1ODliYmY3YzRhZTZlMTVjYzYzNjZhYjNmNzZkYTU2ZiJ9";

    @Test
    public void setAvatar_changePhoto(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("v", VK_version)
//                .queryParam("server", serverCredentials.getServer())
//                .queryParam("photo", serverCredentials.getPhoto())
//                .queryParam("hash", serverCredentials.getHash())
                .queryParam("server", server)
                .queryParam("photo", photo)
                .queryParam("hash", hash)
                .post("/method/photos.saveOwnerPhoto")
                .then().log().body()
                .extract().jsonPath();
    }
//РАБОТА С ДОКУМЕНТАМИ
    @Test
    public void docLoad(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("v", VK_version)
                .get("/method/docs.getTypes")
                .then()
                .log().body()
                .extract().jsonPath();
    }

    @Test
    public void docLoad_getServer(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        String docServerURL = given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("v", VK_version)
                .post("/method/docs.getUploadServer")
                .then()
                .log().body()
                .extract().path("upload_url");
    }

    String docServerURL = "https://pu.vk.com/c237231/upload_doc.php?act=add_doc&mid=13855514&aid=0&gid=0&type=0&hash=552453f95ed42c63102f52000f248fd9&rhash=8993cef6381fda3b4532024b68be992f&api=1";
    DocLoad serverCredentialsDoc = new DocLoad();

    @Test
    public void docLoad_uploadDoc() {
        Specifications.installSpec(Specifications.requestTrelloFileSpec(URL_VK), Specifications.responseSpec(200));
        serverCredentialsDoc = given()
                .when()
                .multiPart("file", new File("D:/SoftDev/Lanit/this_num.txt"), "file/txt")
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("v", VK_version)
                .post(docServerURL)
                .then().log().body()
                .extract().as(DocLoad.class);
        System.out.println(serverCredentialsDoc.getFile());
    }

    String file = "13855514|0|0|237231|b38f44dc0e|txt|571|this_num.txt|a630789c7ecdddf8cc454eab9e93d0b1|ef45365cd5bc13a09245c7d6c8970789||||eyJkaXNrIjoxOX0=";

    @Test
    public void docLoad_saveDoc(){
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        String docId = given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("file", file)
                .queryParam("v", VK_version)
                .post("/method/docs.save")
                .then().log().body()
                .extract().path("doc_id");
    }

    String docId = "630225501";

    @Test
    public void doc_edit() {
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        given()
                .when()
                .header("Authorization", "Bearer "+ VK_TOKEN)
                .queryParam("doc_id", docId)
                .queryParam("title", "test-doc123")
                .queryParam("v", VK_version)
                .post("/method/docs.edit")
                .then().log().body()
                .extract().jsonPath();
    }

//    РАБОТА С ЛЕНТОЙ
    @Test // тут надо коллекцию получить и лайкнуть 5ю новость stream
    public void likeFifthSugNews() {
        Specifications.installSpec(Specifications.requestVKSpec(URL_VK), Specifications.responseSpec(200));
        given()
                .when()
                .header("Authorization", "Bearer " + VK_TOKEN)
                .queryParam("v", VK_version)
                .post("/method/newsfeed.getRecommended")
                .then().log().body()
                .extract().jsonPath();
    }

    //    @Test
//    public static String postMessageToTheWall(String message){
//        String request = String.format("%s?owner_id=%s&message=%s&access_token=%s&v=%s", "wall.post", OWNER_ID, message, TOKEN, API_VERSION);
//    }
//    https://api.vk.com/method/users.get?user_id=210700286&v=5.131


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
