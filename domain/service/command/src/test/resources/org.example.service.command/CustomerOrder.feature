Feature: customer order

      ### Success Scenarios ###

  Scenario: JP makes an order from cart
    Given Customer JP
    And Products:
      | Products |
      | Shoes    |
      | Tshirt   |
      | Jeans    |
    And JP has a Cart with items:
      | Products |
      | Shoes   |
      | Jeans   |
    When customer JP orders with cart
    Then expect the result is ok
    And expect order result contain Products:
      | Products |
      | Shoes   |
      | Jeans   |
    And expect order was created for JP with Products:
      | Products |
      | Shoes   |
      | Jeans   |


    ### Functional Errors ###
  
  Scenario: Customer not found
    Given Customer JP
    And Products:
      | Products |
      | Shoes    |
      | Tshirt   |
      | Jeans    |
    And JP has a Cart with items:
      | Products |
      | Shoes   |
      | Jeans   |
    When customer Parsa orders with cart
    Then expect the result is unsuccessful with error "CUSTOMER_NOT_FOUND"

  Scenario: JP makes an order from cart but product is out of stock
    Given Customer JP
    And Products:
      | Products |
      | Shoes    |
      | Tshirt   |
      | Jeans    |
    And JP has a Cart with items:
      | Products |
      | Shoes   |
      | Jeans   |
    And Product Shoes is out of stock
    When customer JP orders with cart
    Then expect the result is unsuccessful with error "PRODUCT_OUT_OF_STOCK"


    ### Functional Errors ###


  Scenario: customer makes an order results in technical error
    When customer JP order fails with error
    Then expect result is error

  Scenario: customer makes an order results in technical exception Storage not found
    When customer JP order fails with exception STORAGE_NOT_FOUND
    Then expect result is error STORAGE_NOT_FOUND

