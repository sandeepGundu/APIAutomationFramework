@regression @singleUser #@getUser @smoke
Feature: Get single user from Reqres API

  # Positive: Get user by valid ID
  @smoke @positive
  Scenario: TC002 - Get single user (valid)
    Given the Reqres API is available
    When I send a GET request to "/api/users/2"
    Then the response status code should be 200
    And the response JSON path "data.id" should be 2

  @negative
  Scenario: TC003 - Get single user (invalid ID)
    Given the Reqres API is available
    When I send a GET request to "/api/users/23" with API token
    Then the response status code should be 404
    And the response body should be empty

  @negative
  Scenario: TC003 - Get single user (invalid ID)
    Given the Reqres API is available
    When I send a GET request to "/api/users/23" with token key "" and value ""
    Then the response status code should be 404
    And the response body should be empty

