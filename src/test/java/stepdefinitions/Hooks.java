package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtil;

public class Hooks
{
    private static final Logger logger = LoggerUtil.getLogger(Hooks.class);

    @Before
    public void beforeScenario() {
        // E.g., setup base URI, reset data, setup DB connection
        System.out.println(">>> Starting scenario");
        logger.info("==> Scenario Setup: Starting API Test");
        Allure.step("==> Scenario Setup: Starting API Test");
        // You can set baseURI here if you prefer central control
        // RestAssured.baseURI = "https://reqres.in";
    }

    @After
    public void afterScenario() {
        // E.g., cleanup, logout, close DB connection
        System.out.println(">>> Ending scenario");
        logger.info("==> Scenario Teardown: Completed API Test");
        Allure.step("==> Scenario Teardown: Completed API Test");
    }
}
