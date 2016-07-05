Feature: Orders
  In order to complete the information on the place
  I need an address

Scenario: Create Order
  Given the client knows the following request JSON:
   """
   {
  "customerName": "Igor",
  "expectedShipDate": "2016-06-27T03:57:31.195Z",
  "orderLines": [
    {
      "productId": 1234,
      "productName": "Test Item",
      "quantity": 1
    }
  ],
  "orderNumber": "00001003",
  "orderPlacedDate": "2016-06-27T03:57:31.195Z",
  "orderStatus": "New",
  "salesTax": 0.8,
  "sellingPrice": 10.02,
  "shippingAddress": {
    "city": "Redwood City",
    "firstName": "Igor",
    "lastName": "Che",
    "state": "CA",
    "strretAddress1": "900 Island Dr",
    "strretAddress2": "St 100",
    "zipCode": "94054"
  },
  "shippingMethod": "Ground",
  "shippingPrice": 1.59
}
   """
  When client requests POST /createOrder
  Then the response should be orderId