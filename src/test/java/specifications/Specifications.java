package specifications;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.authentication.OAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

    public static RequestSpecification requestVKSpec(String vk_url){

        return new RequestSpecBuilder()
                .setBaseUri(vk_url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification requestTrelloFileSpec(String url){

        return new RequestSpecBuilder()
//                .setAuth(OAuthScheme())
                .setBaseUri(url)
                .setContentType(ContentType.MULTIPART)
                .build();
    }

    public static ResponseSpecification responseSpec(int code){
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static void installSpec(RequestSpecification req, ResponseSpecification resp){
        RestAssured.requestSpecification = req;
        RestAssured.responseSpecification = resp;
    }
}
