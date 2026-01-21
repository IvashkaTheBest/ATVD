import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostmanTest {
    private static final String url = "https://ecdb34a5-da83-405d-8128-d0693921a476.mock.pstmn.io";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .build();
    }

    @Test
    public void testNameSucsses() {
        Response response = given().get("/ownerName/sucsess");
        response.then().statusCode(HttpStatus.SC_OK);
        System.out.println("відповідь від /ownerName/sucsess");
        System.out.println(response.getBody().asString());
        System.out.println("\n");
    }

    @Test
    public void testNameUnsuccess() {
        Response response = given().get("/ownerName/unsuccess");
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        System.out.println("відповідь від /ownerName/unsuccess");
        System.out.println(response.getBody().asString());
        System.out.println("\n");
    }

    @Test
    public void testCreateSomething() {
        Response response = given().post("/createSomething?permission=yes");
        response.then().statusCode(HttpStatus.SC_OK);
        System.out.println("відповідь від /createSomething?permission=yes");
        System.out.println(response.getBody().asString());
        System.out.println("\n");
    }

    @Test
    public void testCreateSomethingWithoutPermission() {
        Response response = given().post("/createSomething");
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        System.out.println("відповідь від /createSomething");
        System.out.println(response.getBody().asString());
        System.out.println("\n");
    }

    @Test
    public void testPutMethod() {
        Map<String, ?> body = Map.of (
                "name", "",
                "surname", ""
        );

        Response response = given().body(body).put("/updateMe");
        response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("відповідь від /updateMe (її не буде)" );
        System.out.println(response.getBody().asString());
        System.out.println("\n");
    }

    @Test
    public void testDeleteMethod() {
        Response response = given().delete("/deleteWorld?SessionID=123456789");
        response.then().statusCode(HttpStatus.SC_GONE);
        System.out.println("відповідь від /deleteWorld ");
        System.out.println(response.getBody().asString());
        System.out.println("\n");
    }
}
