package stepdefinitions.auth;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import utils.ApiUtils;
import utils.ConfigLoader;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;
import  org.testng.*;
import utils.TestContext;

import static io.restassured.RestAssured.*;

public class LoginSteps
{
    private static final Logger logger = LoggerUtil.getLogger(LoginSteps.class);
    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @When("I send a POST request to {string} with email {string} and password {string} and api Token key")
    public void sendLoginRequest(String endpoint, String email, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        Map<String, String> authHeaders = ApiUtils.resolveAuth();  // fallback token from config

        logger.info("POST login to " + endpoint + " with headers: " + authHeaders);
        Allure.step("Login request: " + endpoint + " | Body: " + payload + " | Headers: " + authHeaders);

        if(authHeaders != null && !authHeaders.isEmpty())
        {
            context.response = ApiUtils.postWithToken(endpoint, authHeaders, payload);

            logger.info("Login response: " + context.response.asString());
            Allure.step("Response Code: " + context.response.statusCode());
        }

    }

    @Then("the login response should contain token")
    public void verifyLoginResponse() {
        String token = context.response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertTrue(token != null && !token.trim().isEmpty(), "Token should not be null or empty");
        logger.info("Received token: " + token);
        Allure.step("Verified token in login response: " + token);
    }

    @When("I send a POST request to {string} with email {string} and password {string} and token key {string} and value {string}")
    public void sendLoginRequestWithCustomToken(String endpoint, String email, String password, String tokenKey, String tokenValue) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        Map<String, String> authHeaders = ApiUtils.resolveAuth(tokenKey, tokenValue);

        logger.info("Sending login POST request to " + endpoint + " with headers: " + authHeaders);
        Allure.step("Login request to " + endpoint + " with headers: " + authHeaders + " and body: " + payload);

        if(authHeaders != null && !authHeaders.isEmpty())
        {
            context.response = ApiUtils.postWithToken(endpoint, authHeaders, payload);

            logger.info("Login POST response: " + context.response.asString());
            Allure.step("Response Code: " + context.response.statusCode());
        }

    }
}
