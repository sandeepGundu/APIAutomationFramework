package stepdefinitions.users;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.ApiUtils;
import utils.ConfigLoader;
import utils.LoggerUtil;
import utils.TestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


@Severity(SeverityLevel.CRITICAL)
//@Description("TC001 - Get users on page 2 from Reqres API")
@Epic("User API Tests")
@Feature("GET /api/users?page=2")
public class UsersSteps
{
    //private Response response;
    private static final Logger logger = LoggerUtil.getLogger(UsersSteps.class);
    private final TestContext context;

    public UsersSteps(TestContext context) {
        this.context = context;
    }

    @Given("the Reqres API is available")
    public void the_api_is_available() {
        RestAssured.baseURI = "https://reqres.in";
        logger.info("Base URI set to: " + RestAssured.baseURI);
        Allure.step("Base URI set to: " + RestAssured.baseURI); // Example
    }

    @When("I send a GET request to {string}")
    public void i_send_get_request_to(String endpoint) {
        logger.info("Sending GET request to endpoint: " + endpoint);
        Allure.step("Sent GET request to: " + endpoint);
        context.response = ApiUtils.get(endpoint);
    }

    @When("I send a GET request to {string} with API token")
    public void i_send_get_request_with_API_Token(String endpoint) {
        /*Map<String, String> apiToken = new HashMap<>();
        String tokenKey = ConfigLoader.get("api.token.key");
        String tokenValue = ConfigLoader.get("api.token.value");*/

        Map<String, String> authHeader = ApiUtils.resolveAuth(); // Fallback from config

        logger.info("Sending GET request with headers to endpoint: " + endpoint);
        Allure.step("Sent GET request to: " + endpoint + " with headers:" + authHeader);

        context.response = ApiUtils.getWithToken(endpoint, authHeader);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_should_be(int expectedStatusCode) {
        logger.info("Asserting response code: expected = " + expectedStatusCode +
                ", actual = " + context.response.getStatusCode());
        Allure.step("Response code received: " + context.response.getStatusCode());
        Assert.assertEquals(context.response.statusCode(), expectedStatusCode, "Incorrect status code!");
    }

    @Then("the response should contain a list of users")
    public void the_response_should_contain_user_list() {
        logger.info("Validating user list in response...");
        List<?> users = context.response.jsonPath().getList("data");
        Assert.assertNotNull(users, "User list is null");
        Assert.assertTrue(users.size() > 0, "User list is empty");
    }

    @Then("the response JSON path {string} should be {int}")
    public void validateJsonPathInt(String path, int expected) {
        int actual = context.response.jsonPath().getInt(path);
        Assert.assertEquals(actual, expected);
        Allure.step("JSON validation: " + path + " == " + expected);
    }

    @Then("the response body should be empty")
    public void verifyEmptyBody() {
        String body = context.response.body().asString().trim();
        Assert.assertTrue(body.equals("{}") || body.isEmpty(), "Expected empty or {} but got: " + body);
        Allure.step("Verified empty response body or {}");
    }

    @When("I send a POST request to {string} with name {string} and job {string} and token key {string} and value {string}")
    public void sendPostToCreateUserWithCustomToken(String endpoint, String name, String job, String tokenKey, String tokenValue) {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);

        Map<String, String> authHeaders = ApiUtils.resolveAuth(tokenKey, tokenValue);

        logger.info("Sending POST request to " + endpoint + " with payload: " + payload + " and headers: " + authHeaders);
        Allure.step("POST " + endpoint + " with body: " + payload + " and headers: " + authHeaders);

        if(authHeaders != null && !authHeaders.isEmpty())
        {
            context.response = ApiUtils.postWithToken(endpoint, authHeaders, payload);

            logger.info("Response: " + context.response.asString());
            Allure.step("Response received: " + context.response.asString());
        }
    }

    @When("I send a POST request to {string} with name {string} and job {string} and api Token key")
    public void sendPostToCreateUserWithDefaultToken(String endpoint, String name, String job) {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);

        //Map<String, String> authHeaders = ApiUtils.resolveAuth(tokenKey, tokenValue);
        Map<String, String> authHeaders = ApiUtils.resolveAuth(); // fallback

        logger.info("Sending POST request to " + endpoint + " with payload: " + payload + " and default headers: " + authHeaders);
        Allure.step("POST " + endpoint + " with body: " + payload + " and default headers: " + authHeaders);

        if(authHeaders != null && !authHeaders.isEmpty())
        {
            context.response = ApiUtils.postWithToken(endpoint, authHeaders, payload);

            logger.info("Response received: " + context.response.asString());
            Allure.step("Response received: " + context.response.asString());
        }
    }

    @Then("the response JSON path {string} should be {string}")
    public void validateResponseJsonString(String path, String expected) {
        String actual = context.response.jsonPath().getString(path);
        logger.info("Asserting JSON path " + path + ": expected = " + expected + ", actual = " + actual);
        Allure.step("Validated JSON " + path + " = " + expected);
        Assert.assertEquals(actual, expected);
    }

    @When("I send a PUT request to {string} with name {string} and job {string} and api Token key")
    public void sendPutRequestWithPayload(String endpoint, String name, String job) {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);

        /*String tokenKey = ConfigLoader.get("api.token.key");
        String tokenValue = ConfigLoader.get("api.token.value");*/
        Map<String, String> authHeaders = ApiUtils.resolveAuth(); // fallback
        //Map<String, String> authHeader = ApiUtils.resolveAuth(keyOverride, valueOverride);

        logger.info("Sending PUT request to " + endpoint + " with payload: " + payload);
        Allure.step("PUT " + endpoint + " with body: " + payload + " and headers: " + authHeaders);
        //Allure.step("PUT " + endpoint + " with body: " + payload);

        if(authHeaders != null && !authHeaders.isEmpty())
        {
            context.response = ApiUtils.putWithToken(endpoint, authHeaders, payload);

            logger.info("Response of PUT Request: " + context.response.asString());
            Allure.step("Response received: " + context.response.asString());
        }

    }

    @When("I send a DELETE request to {string} with token key {string} and value {string}")
    public void sendDeleteRequestWithDynamicToken(String endpoint, String tokenKey, String tokenValue) {
        Map<String, String> authHeaders = ApiUtils.resolveAuth(tokenKey, tokenValue);

        logger.info("Sending DELETE request to " + endpoint + " with headers: " + authHeaders);
        Allure.step("DELETE " + endpoint + " with headers: " + authHeaders);

        if(authHeaders != null && !authHeaders.isEmpty())
        {
            context.response = ApiUtils.deleteWithToken(endpoint, authHeaders);

            logger.info("Response of DELETE Request: " + context.response.asString());
            Allure.step("Response of DELETE received: " + context.response.asString());
        }

    }

    @When("I send a PUT request to {string} with token key {string} and value {string} and the following data:")
    public void sendPutRequestWithDataTable(String endpoint, String tokenKey, String tokenValue, DataTable dataTable) {
        // Parse the DataTable into a Map
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> payload = rows.get(0); // Assuming single row for simplicity

        // Log and report the request
        logger.info("Sending PUT request to " + endpoint + " with payload: " + payload + " and token: " + tokenKey + "=" + tokenValue);
        Allure.step("PUT " + endpoint + " with body: " + payload + " and token: " + tokenKey + "=" + tokenValue);

        // Send the PUT request
        Map<String, String> headers = ApiUtils.resolveAuth(tokenKey, tokenValue);
        context.response = ApiUtils.putWithToken(endpoint, headers, payload);

        // Log and report the response
        logger.info("Response of PUT Request: " + context.response.asString());
        Allure.step("Response received: " + context.response.asString());
    }

}
