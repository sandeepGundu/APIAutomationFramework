@regression @deleteUser
Feature: Delete user from Reqres API

  # Positive: Delete user by valid ID
  @positive
  Scenario: TC011 - Delete user by ID
    Given the Reqres API is available
    When I send a DELETE request to "/api/users/2" with token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 204

  # Negative: Delete invalid user
  @negative
  Scenario: TC012 - Delete non-existent user
    Given the Reqres API is available
    When I send a DELETE request to "/api/users/999" with token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 404