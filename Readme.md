# INVOICES App
Program for issuing and recording invoices written in Java.
The project consists of a backend layer based on REST API and a frontend layer based on the Vaadin library.

## Tools and libraries:
- BACKEND:
  - Java 11
  - IntelliJ IDEA
  - Gradle version 6.8.3
  - Spring Boot 2.6.2
  - Lombok version 1.18.22
  - JUnit4
  - Hibernate
  - MySQL Database
- FRONTEND:
  - Java 11
  - Gradle version 6.8.3
  - Spring Boot 2.6.2
  - Lombok version 1.18.22
  - IntelliJ IDEA
  - Vaadin 14.8.1

## Main assumptions:
- For business purposes I used soft delete in the app, to avoid permanent deletion of records.
- I used two external APIs in the application:
  - retrieving the customer's data from the CEIDG database based on his NIP (used in the Customers endpoint),
  - retrieving the name of the city based on the given zip code (used in the Customers and Users endpoints).
- I used two design patterns:
  - facade (in the backend),
  - enum type (in the frontend). 
- This app has 6 groups of endpoints (documented in Swagger - run app and open in a browser API documentation):

| Group of Endpoints | Description                    | 	Http Methods                                                         | 
|--------------------|--------------------------------|-----------------------------------------------------------------------|
| Customers          | Clients                        | GET(all, by ID), POST, PUT(add and delete) Customers                  |
| Products           | Products available             | GET(all, by ID), POST, PUT(add and delete) Products                   |
| Invoices           | Invoices issued                | GET(all, by ID), POST, PUT(add and delete Invoice, Customer, Product, User) |
| Users              | Reseller                       | GET(all, by ID), POST, PUT(add and delete) Users                      |
| CEIDG API          | External database of customers | GET(by tax ID)                                                        |
| Postcode Api       | External database of zip codes | GET(by zip code)                                                      |

## How to run application
- Clone or download from repository, and build a project.
- Prepare database in accordance with application.properties file located in resources package (main package).
- To see how application works:
  - first run the backend app (InvoiceApplication.class),
  - next run the frontend app (InvoicesFrontendApplication.class).

## Ideas for improvement
I would like to add:
- User logging,
- generating invoices to pdf and printing them.

## Links:
- backend repo: https://github.com/gtrofimiec/Invoices.git
- last commit: https://github.com/gtrofimiec/invoices_frontend/tree/b165fd806b37df544dfe093af78c89e680afede4