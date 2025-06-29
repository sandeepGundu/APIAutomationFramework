@login @regression
Feature: Login to Reqres API

  # Positive: Valid login
  @positive
  Scenario: Successful login
    Given the Reqres API is available
    When I send a POST request to "/api/login" with email "eve.holt@reqres.in" and password "cityslicka" and api Token key
    Then the response status code should be 200
    And the login response should contain token

  # Negative: Invalid credentials
  @negative
  Scenario: TC014 - Login with wrong password
    Given the Reqres API is available
    When I send a POST request to "/api/login" with email "eve.holt@reqres.in" and password "wrongpass" and token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 400
    #But we are getting the Status Code as 200, need to update the above step as below
    #Then the response status code should be 200

  @negative @security
  Scenario: TC014 - Login with wrong password and missing token
    Given the Reqres API is available
    When I send a POST request to "/api/login" with email "eve.holt@reqres.in" and password "wrongpass" and token key "" and value ""
    Then the response status code should be 401

  @negative @security
  Scenario: Login with missing password
    Given the Reqres API is available
    When I send a POST request to "/api/login" with email "eve.holt@reqres.in" and password "" and token key "" and value ""
    Then the response status code should be 401