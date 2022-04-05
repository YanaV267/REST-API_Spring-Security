### REST API Basics : Gift Certificate System

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

#### Application requirements

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
13. Repository layer should be tested using integration tests with an in-memory embedded database (all operations with certificates).

#### General requirements

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