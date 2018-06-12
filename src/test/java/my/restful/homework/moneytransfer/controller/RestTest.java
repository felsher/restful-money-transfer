package my.restful.homework.moneytransfer.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * This test can work properly only when application is running on localhost:5555
 * and only one time per application up-time period.
 * Yes it's not really cool..
 * */
@Ignore //just cause it's not working without application running locally
public class RestTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = 5555;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void complexApiTest() {
        Map<String, Object> mapBody = new HashMap<>();
        mapBody.put("balance", 1000.0);

        createAccount(mapBody)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(containsString(
                        "{\n" +
                                " \"id\": 0\n" +
                                "}")
                );

        testGetAccount();
        testGetAccounts();

        createAccount(mapBody);
        testTransfer();

        testDeposit();
        testDeleteAccount();
    }

    private Response createAccount(Map<String, Object> mapBody) {
        return given()
                .contentType("application/json")
                .body(mapBody)
                .when().post("/accounts");
    }

    private void testGetAccounts() {
        given()
                .when().get("/accounts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(containsString(
                        "{\n" +
                                "\"result\":[\n" +
                                "{\n" +
                                "  \"id\" : 0,\n" +
                                "  \"balance\" : 1000.0\n" +
                                "}\n" +
                                "]\n" +
                                "}")
                );
    }

    private void testGetAccount() {
        given()
                .when().get("/accounts/0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(containsString("{\n" +
                        "  \"id\" : 0,\n" +
                        "  \"balance\" : 1000.0\n" +
                        "}")
                );
    }

    private void testDeleteAccount() {
        given()
                .when().delete("/accounts/0")
                .then()
                .statusCode(204);
    }

    private void testDeposit() {
        Map<String, Object> mapBody = new HashMap<>();
        mapBody.put("accountId", 1);
        mapBody.put("amount", 500.0);

        given()
                .contentType("application/json")
                .body(mapBody)
                .when().post("/accounts/deposit")
                .then()
                .statusCode(204);
    }

    private void testTransfer() {
        Map<String, Object> mapBody = new HashMap<>();
        mapBody.put("fromAccountId", 0);
        mapBody.put("toAccountId", 1);
        mapBody.put("amount", 300.0);

        given()
                .contentType("application/json")
                .body(mapBody)
                .when().post("/transfers")
                .then()
                .statusCode(204);
    }

}
