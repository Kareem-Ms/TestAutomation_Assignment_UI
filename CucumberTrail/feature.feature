Feature: Checkout Test Scenario

  @Regression @Critical
  Scenario: Verify Login with valid credentials
    Given I am on the login page
    When I login with valid credentials
    Then I should see the product title

  @Regression
  Scenario: Verify adding the most expensive n products to the cart
    Given I am logged in
    When I add the most expensive products to the cart
    Then the products should be in the cart

  @Regression
  Scenario: Verify completing checkout process
    Given I have products in the cart
    When I complete the checkout process
    Then I should see the confirmation message