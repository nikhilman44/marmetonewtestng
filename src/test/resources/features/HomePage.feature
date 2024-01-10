Feature: Home Page
  as a user of moviesapp
  i want to able to visible home page properly
  so that i can view movies do some actions on home page

  Scenario: Home Page
    Given am on Home page
    Then i can view Home page with all elements

  Scenario: Home header section UI
    Given am on Home page
    Then header section Ui display as this

  Scenario: Home header section functionality
    Given am on Home page
    And Clicking navbar Links

  Scenario:  Contact us Section
    Given am on Home page
    Then Should display contact section in bottom