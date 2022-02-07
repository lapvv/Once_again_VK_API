package specifications;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

    public static RequestSpecification requestVKSpec(String vk_url){
//        String Trello_key = System.getProperty("TRELLO_KEY");
//        String Trello_token = System.getProperty("TRELLO_TOKEN");

        return new RequestSpecBuilder()
                .setBaseUri(vk_url)
                .setContentType(ContentType.JSON)
//                .addHeader("Authorization: OAuth oauth_consumer_key=\"{{apiKey}}\", oauth_token=\"{{apiToken}}\"", Trello_key, Trello_token)
                .build();
    }

    public static ResponseSpecification responseSpec(int code){
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .build();
    }

    public static void installSpec(RequestSpecification req, ResponseSpecification resp){
        RestAssured.requestSpecification = req;
        RestAssured.responseSpecification = resp;
    }
}
