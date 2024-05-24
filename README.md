# DB Auction System

This is a simple auction system that allows users to create auctions and bid on them. The system is implemented using the Spring Boot and H2 database.

## Modules in the system
- **db-auction-server**
    * Hosts the business logic for the auction system, enabling the creation of products, auctions, and bids.
    * Runs on port 8092.
- **db-auction-user-service**
    * Manages user accounts, including user verification and creation.
    * Runs on port 8091.
- **db-auction-gateway-service**
    * Routes requests to the appropriate service and acts as the system's entry point.
    * Runs on port 8090.

## Implementation Details

### db-auction-server

#### Creating a Product
* Before creating an auction, a product must be created.
  * Endpoint: `POST /products`
  * Request Body
    ```json
    {
        "name": "Product X",
        "description": "This is Product X",
        "price": 100.0,
         "quantity": 10
    }
    ```
  * Response
      ```json
      {
          "id": "2889387d-bdca-423e-bc74-15244882227b",
          "name": "Product X",
          "description": "This is Product X",
          "price": 100.0,
          "quantity": 10
      }
      ```
#### Creating an Auction
* To create an auction for a product:
    * Endpoint: `POST /auctions`
    * Request Body
    ```json
    {
        "name": "Auction for Product X",
        "description": "This is an auction for Product X",
        "productId": "2889387d-bdca-423e-bc74-15244882227b",
        "minPrice": 100.0,
        "startDate": "2024-05-22T00:00:00",
        "endDate": "2024-05-25T00:00:00",
        "sellerId": "7aae59f6-7fa5-4408-830e-5efe4b8f6819"
    }
    ```
#### Bidding on an Auction
* To place a bid on a running auction:
    * Endpoint: `POST /bids`
    * Request Body
    ```json
     {
      "auctionId": "93d50bf4-eb88-4bc8-925f-2f1f5cf294ab",
      "bidderId": "ccf7540f-16eb-4c36-bcb1-fd32a8baa940",
      "productId": "2889387d-bdca-423e-bc74-15244882227b",
      "bidAmount": 150.0
      }    
    ```
  This API supports multiple bids for an auction from various users. It validates the bid by ensuring the auction and bidder exist, the auction is valid, the product exists, and the bid amount meets the minimum pricing requirement.

#### Completing an Auction
* To complete an auction:
* Endpoint: `POST /auctions/{auctionId}/complete`
* This API completes the auction, changes the auction status, winning bid id, and winning bid amount.

### Testing
* Unit tests for the controller and service layers are written using JUnit and Mockito.

### Enhancements, but couldn't be done due to time constraints
* **Integration Tests:** Comprehensive tests to validate the complete system flow
* **JWT Authentication:** Improve user validation by generating and using JWT tokens instead of direct user ID checks. Authenticate users through `/login` to validate credentials and issue tokens.
* **Resilience4j Integration:** Since we are using microservices based architecture, we use Resilience4j to create Circuit Breaker and implement fault tolerance that could save the system from cascading failures.
* **MDC Context:** We can use MDC context to the request while it is flowing throw various services. This will help in debugging the request easily.