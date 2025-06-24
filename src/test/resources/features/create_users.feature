@regression #@getUser @smoke
Feature: Create a new user

  Scenario: TC004 - Create a new user (valid)
    Given the Reqres API is available
    When I send a POST request to "/api/users" with name "John" and job "Developer" and api Token key
    Then the response status code should be 201
    And the response JSON path "name" should be "John"
    And the response JSON path "job" should be "Developer"