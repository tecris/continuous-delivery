@component:ui
@user-story:US123
@version:Release-MVP-A
@run_US123
Feature: 1.0 Author management
  As a bookstore application administrator 
  I want to be able to add, modify, delete authors in bookstore application 
  So that bookstore application contains up-2-date author information  
  
  Scenario Outline: 1.0-AC01 - Enter new author
    Given a bookstore application administrator navigates to create author page
    When a new author with first name '<first_name>', last name '<last_name>', email '<email>' is created
    Then author list shows newly created author with first name '<first_name>', last name '<last_name>', email '<email>'
  Examples:
    | first_name   | last_name | email                       |
    | Peter        | Abelard   | peter.abelard@bookstore.org |
    | Isaac        | Alfasi    | isaac.alfasi@bookstore.org  |