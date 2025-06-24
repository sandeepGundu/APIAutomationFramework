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
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        logger.info("Sending POST to " + endpoint);
        Allure.step("Sending POST to " + endpoint);
        Allure.step("Sending login request to " + endpoint + " with body: " + body);

        String tokenKey = ConfigLoader.get("api.token.key");
        String tokenValue = ConfigLoader.get("api.token.value");

        context.response = ApiUtils.postWithToken(endpoint, tokenKey, tokenValue, body);

        logger.info("Login response: " + context.response.asString());
        Allure.step("Response Code: " + context.response.statusCode());
    }

    @Then("the login response should contain token")
    public void verifyLoginResponse() {
        String token = context.response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
        logger.info("Received token: " + token);
        Allure.step("Verified token in login response: " + token);
    }
}
