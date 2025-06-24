@regression @createUser #@getUser @smoke
Feature: Create a new user

  # Positive: Basic POST with token
  @positive
  Scenario: TC004 - Create a new user (valid)
    Given the Reqres API is available
    When I send a POST request to "/api/users" with name "John" and job "Developer" and api Token key
    Then the response status code should be 201
    And the response JSON path "name" should be "John"
    And the response JSON path "job" should be "Developer"

  @positive
  Scenario: TC006 - Create a new user with token
    Given the Reqres API is available
    When I send a POST request to "/api/users" with name "John" and job "Developer" and token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 201
    And the response JSON path "name" should be "John"
    And the response JSON path "job" should be "Developer"

  # Positive: Create user using DataTable
  @positive
  Scenario: TC007 - Create user with DataTable
    Given the Reqres API is available
    When I send a POST request to "/api/users" with token key "x-api-key" and value "reqres-free-v1" and the following data:
      | name   | job     |
      | Alice  | Tester  |
    Then the response status code should be 201
    And the response JSON path "name" should be "Alice"
    And the response JSON path "job" should be "Tester"

  # Negative: Missing job field
  @negative
  Scenario: TC008 - Create user with missing job
    Given the Reqres API is available
    When I send a POST request to "/api/users" with name "Bob" and job "" and token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 400