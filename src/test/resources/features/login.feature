@login #@regression
Feature: Fetch a single user
  Scenario: Successful login
    Given the Reqres API is available
    When I send a POST request to "/api/login" with email "eve.holt@reqres.in" and password "cityslicka" and api Token key
    Then the response status code should be 200
    And the login response should contain token