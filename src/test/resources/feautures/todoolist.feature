Feature: User Registration on Basketball England websitee

  Scenario: Successful user registration
    Given the user is on the registration page
    When the user enters valid details
    And the user submits the form
    Then the account should be created successfully

  Scenario: Registration fails due to missing last name
    Given the user is on the registration page
    When the user enters details with missing last name
    And the user submits the form
    Then an error message for missing last name should be displayed

  Scenario: Registration fails due to password mismatch
    Given the user is on the registration page
    When the user enters details with mismatching passwords
    And the user submits the form
    Then an error message for password mismatch should be displayed

  Scenario: Registration fails due to terms and conditions not accepted
    Given the user is on the registration page
    When the user enters details but does not accept terms and conditions
    And the user submits the form
    Then an error message for not accepting terms and conditions should be displayed