Feature: Update Users
  Scenario: TC005 - Update existing user (valid)
    Given the Reqres API is available
    When I send a PUT request to "/api/users/2" with name "neo" and job "zion leader" and api Token key
    Then the response status code should be 200
    And the response JSON path "name" should be "neo"
    And the response JSON path "job" should be "zion leader"