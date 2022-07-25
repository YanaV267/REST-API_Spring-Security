## REST API & Security : Gift Certificate System

### General application requirements

1. JDK version: 8 – usage of Streams, java.time.*, etc.
2. Application packages root: com.epam.esm
3. Any widely-used connection pool could be used.
4. Spring JDBC Template should be used for data access.
5. Use transactions where it’s necessary.
6. Java Code Convention is mandatory (exception: margin size – 120 chars).
7. Build tool: Gradle. Multi-module project.
8. Web server: Apache Tomcat.
9. Application container: Spring IoC. Spring Framework.
10. Database: MySQL.
11. Testing: JUnit 5.+, Mockito.
12. Service layer should be covered with unit tests not less than 80%.

#### Other general requirements

1. Code should be clean and should not contain any “developer-purpose” constructions.
2. App should be designed and written with respect to OOD and SOLID principles.
3. Code should contain valuable comments where appropriate.
4. Public APIs should be documented (Javadoc).
5. Clear layered structure should be used with responsibilities of each application layer defined.
6. JSON should be used as a format of client-server communication messages.
7. Convenient error/exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side. Example: handle 404 error:

        • HTTP Status: 404
        • response body    
        • {
        • “errorMessage”: “Requested resource not found (id = 55)”,
        • “errorCode”: 40401
        • }

   where *errorCode” is your custom code (it can be based on http status and requested resource - certificate or tag)
8. Abstraction should be used everywhere to avoid code duplication.
9. Several configurations should be implemented (at least two - dev and prod).

###Module 2
#### Business requirements
1. Develop web service for Gift Certificates system with the following entities (many-to-many):
   ![](model.png)\
    - *CreateDate*, *LastUpdateDate* - format *ISO 8601* (example: 2018-08-29T06:12:15.156)
    - *Duration* - in days (expiration period)
2. The system should expose REST APIs to perform the following operations:
    - CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
    - CRD operations for Tag.
    - Get certificates with tags (all params are optional and can be used in conjunction):
        - by tag name (ONE tag)
        - search by part of name/description (can be implemented, using DB function call)
        - sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
        
###Module 3

#### Part 1

Migrate your existing Spring application from a previous module to a Spring Boot application.

#### Part 2

##### Business requirements

The system should be extended to expose the following REST APIs:
1. Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
2. Add new entity User.
   * implement only get operations for user entity.
3. Make an order on gift certificate for a user (user should have an ability to buy a certificate).
4. Get information about user’s orders.
5. Get information about user’s order: cost and timestamp of a purchase.
   * The order cost should not be changed if the price of the gift certificate is changed.
6. Get the most widely used tag of a user with the highest cost of all orders.
   * Create separate endpoint for this query.
7. Search for gift certificates by several tags (“and” condition).
8. Pagination should be implemented for all GET endpoints. 
9. Support HATEOAS on REST endpoints.

#### Part 3

This sub-module covers following topics:
1. ORM
2. JPA & Hibernate
3. Transactions

##### Application requirements

1. Hibernate should be used as a JPA implementation for data access.
2. Spring Transaction should be used in all necessary areas of the application.
3. Audit data should be populated using JPA features (an example can be found in materials).

###Module 4

##### Application requirements

1. Spring Security should be used as a security framework.
2. Application should support only stateless user authentication and verify integrity of JWT token.
3. Users should be stored in a database with some basic information and a password.

User Permissions:

     - Guest:
        * Read operations for main entity.
        * Signup.
        * Login.
     - User:
        * Make an order on main entity.
        * All read operations.
     - Administrator (can be added only via database call):
        * All operations, including addition and modification of entities.

4. Using Oauth2 as an authorization protocol.
   a. OAuth2 scopes should be used to restrict data.
   b. Implicit grant and Resource owner credentials grant should be implemented.
5. All repository (and existing ones) must be migrated to Spring Data.