@regression @getUser
Feature: Get list of users from Reqres API

  # Positive: Basic GET request for users
  @smoke @positive
  Scenario: TC001 - Get list of users on page 2
    Given the Reqres API is available
    When I send a GET request to "/api/users?page=2"
    Then the response status code should be 200
    And the response should contain a list of users

    # Negative: Invalid query param
  @negative
  Scenario: TC002 - Get users with invalid page param
    Given the Reqres API is available
    When I send a GET request to "/api/users?page=abc"
    Then the response status code should be 401
    #But getting the status code as 200
    #Then the response status code should be 200

  # Negative: Missing endpoint
  @negative
  Scenario: TC003 - Get users with invalid endpoint
    Given the Reqres API is available
    When I send a GET request to "/api/userzz"
    Then the response status code should be 404
    #But getting the status code as 200
    #Then the response status code should be 200