@regression @updateUser
Feature: Update Users via PUT request

  Scenario: TC005 - Update existing user (valid)
    Given the Reqres API is available
    When I send a PUT request to "/api/users/2" with name "neo" and job "zion leader" and api Token key
    Then the response status code should be 200
    And the response JSON path "name" should be "neo"
    And the response JSON path "job" should be "zion leader"

#  Scenario Outline: TC005 - Update existing user (valid) using Data Table Concept
#    Given the Reqres API is available
#    When I send a PUT request to "/api/users/2" with name <name> and job <job> and api Token key
#    Then the response status code should be 200
#    And the response JSON path "name" should be <name>
#    And the response JSON path "job" should be <job>
#    Examples:
#      | <name> | <job> |
#      | neo  | zion leader |

  # Positive: Full user update with token
  @positive
  Scenario: TC009 - Update user with PUT
    Given the Reqres API is available
    When I send a PUT request to "/api/users/2" with token key "x-api-key" and value "reqres-free-v1" and the following data:
      | name  | job         |
      | Mike  | Senior Dev  |
    Then the response status code should be 200
    And the response JSON path "name" should be "Mike"

  # Negative: Update non-existent user
  @negative
  Scenario: TC010 - Update user with invalid ID
    Given the Reqres API is available
    When I send a PUT request to "/api/users/999" with token key "x-api-key" and value "reqres-free-v1" and the following data:
      | name  | job   |
      | Test  | Lead  |
    Then the response status code should be 404
    #it is giving the status code as 200 since, it is updating the user details for invalid ID
    #Then the response status code should be 200
