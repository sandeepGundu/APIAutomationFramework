package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;
import org.apache.logging.log4j.Logger;

public class ApiUtils
{
    private static final Logger logger = LoggerUtil.getLogger(ApiUtils.class);

    public static String tokenKey = ConfigLoader.get("api.token.key");
    public static String tokenValue = ConfigLoader.get("api.token.value");

    // ---------- GET ----------
    public static Response get(String endpoint) {
        Response response = given().when()
                .get(endpoint)
                .then().extract().response();
        logResponse("GET", endpoint, response);
        return response;
    }

    public static Response getWithToken(String endpoint, String tokenKey, String tokenValue) {
        Response response =  given().header(tokenKey, tokenValue).when()
                .get(endpoint)
                .then().extract().response();
        logResponse("GET", endpoint, response);
        return response;
    }

    // ---------- POST ----------
    public static Response post(String endpoint, Map<String, String> jsonPayload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when().post(endpoint)
                .then().extract().response();
        logResponse("POST", endpoint, response);
        return response;
    }

    public static Response postWithToken(String endpoint, String tokenKey, String tokenValue, Map<String, String> jsonPayload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header(tokenKey, tokenValue)
                .body(jsonPayload)
                .when().post(endpoint)
                .then().extract().response();

        logResponse("POST", endpoint, response);
        return response;
    }

    // ---------- PUT ----------
    public static Response putWithToken(String endpoint, String tokenKey, String tokenValue, Map<String, String> jsonPayload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header(tokenKey, tokenValue)
                .body(jsonPayload)
                .when().put(endpoint)
                .then().extract().response();

        logResponse("PUT", endpoint, response);
        return response;
    }

    // ---------- DELETE ----------
    public static Response deleteWithToken(String endpoint, String tokenKey, String tokenValue) {
        Response response = given()
                .header(tokenKey, tokenValue)
                .when().delete(endpoint)
                .then().extract().response();

        logResponse("DELETE", endpoint, response);
        return response;
    }

    /*public static Response request(String method, String endpoint, Map<String, String> headers, Object body) {
        RequestSpecification spec = given().headers(headers);
        if (body != null) spec.contentType(ContentType.JSON).body(body);
        return spec.request(method, endpoint).then().extract().response();
    }*/

    // ---------- Response Logger ----------
    private static void logResponse(String method, String endpoint, Response response) {
        String msg = method + " " + endpoint +
                " | Status: " + response.statusCode() +
                " | Body: " + response.body().asPrettyString();

        logger.info(msg);
        io.qameta.allure.Allure.step("[" + method + "] Response from " + endpoint +
                " with status " + response.statusCode() +
                ":\n" + response.body().asPrettyString());
    }

    public static Map<String, String> resolveAuth(String overrideKey, String overrideValue) {
        String key = overrideKey != null && !overrideKey.isEmpty()
                ? overrideKey
                : ConfigLoader.get("api.token.key");

        String value = overrideValue != null && !overrideValue.isEmpty()
                ? overrideValue
                : ConfigLoader.get("api.token.value");

        return Map.of(key, value);
    }
}
