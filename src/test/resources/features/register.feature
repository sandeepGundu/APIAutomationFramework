@regression @register
Feature: User registration

  # Positive: Successful registration
  @positive
  Scenario: TC015 - Register user
    Given the Reqres API is available
    When I send a POST request to "/api/register" with email "eve.holt@reqres.in" and password "pistol" and token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 200

  # Negative: Missing password
  @negative
  Scenario: TC016 - Register with missing password
    Given the Reqres API is available
    When I send a POST request to "/api/register" with email "sydney@fife" and password "" and token key "x-api-key" and value "reqres-free-v1"
    Then the response status code should be 400
