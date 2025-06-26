Feature: Verify the posts

  @positive
  Scenario: TC011 - Get list of posts
    Given the JSONPlaceholder API is available
    When I send a GET request to "/posts"
    Then the response status code should be 200
    And the response JSON path "$[0].id" should be 1
