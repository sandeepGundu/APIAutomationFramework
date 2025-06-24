#@regression
Feature: Get list of users from Reqres API

  @smoke
  Scenario: TC001 - Get list of users on page 2
    Given the Reqres API is available
    When I send a GET request to "/api/users?page=2"
    Then the response status code should be 200
    And the response should contain a list of users
