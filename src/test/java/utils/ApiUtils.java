package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;
import org.apache.logging.log4j.Logger;

public class ApiUtils
{
    private static final Logger logger = LoggerUtil.getLogger(ApiUtils.class);

    // ---------- GET ----------
    public static Response get(String endpoint) {
        Response response = given()
                .when()
                .get(endpoint)
                .then().extract().response();
        logResponse("GET", endpoint, response);
        return response;
    }

    public static Response getWithToken(String endpoint, Map<String, String> headers) {
        Response response = given()
                .headers(headers)
                .when().get(endpoint)
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

    public static Response postWithToken(String endpoint, Map<String, String> headers, Map<String, String> jsonPayload) {
        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when().post(endpoint)
                .then().extract().response();

        logResponse("POST", endpoint, response);
        return response;
    }

    // ---------- PUT ----------
    public static Response putWithToken(String endpoint, Map<String, String> headers, Map<String, String> jsonPayload) {
        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when().put(endpoint)
                .then().extract().response();

        logResponse("PUT", endpoint, response);
        return response;
    }

    // ---------- DELETE ----------
    public static Response deleteWithToken(String endpoint, Map<String, String> headers) {
        Response response = given()
                .headers(headers)
                .when().delete(endpoint)
                .then().extract().response();

        logResponse("DELETE", endpoint, response);
        return response;
    }

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

    // For explicit key/value override:
    public static Map<String, String> resolveAuth(String tokenKey, String tokenValue) {
        boolean missingKey = tokenKey == null || tokenKey.trim().isEmpty();
        boolean missingValue = tokenValue == null || tokenValue.trim().isEmpty();

        if (missingKey && missingValue) {
            // Intentionally skip token
            return Collections.emptyMap();
        }

        if (missingKey || missingValue) {
            throw new RuntimeException("Token key/value incomplete: key=" + tokenKey + ", value=" + tokenValue);
        }

        return Map.of(tokenKey, tokenValue);
    }

    // Another method for default (fallback) call:
    public static Map<String, String> resolveAuth() {
        String key = ConfigLoader.get("api.token.key");
        String value = ConfigLoader.get("api.token.value");

        boolean missingKey = false, missingValue = false;

        if (key == null || key.trim().isEmpty()) {
            //throw new RuntimeException("Missing API token key in config");
            missingKey = true;
        }

        if (value == null || value.trim().isEmpty()) {
            //throw new RuntimeException("Missing API token value in config");
            missingValue = true;
        }

        if (missingKey && missingValue) {
            logger.warn("No token supplied — proceeding without authentication.");
            return Collections.emptyMap();
        }
        else {
            assert key != null;
            assert value != null;
            return Map.of(key, value);
        }
    }

}
