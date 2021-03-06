# Shop Place App
REST/JSON web service in Java to manage products and orders, realized with [Spring Boot](https://spring.io/guides/gs/rest-service/).

## How to build
In order to build the project, [Maven](https://maven.apache.org/docs/3.3.9/release-notes.html) is required.

From root folder:
```
mvn clean package
```
Build will be launched, and before executing the packaging, all tests will be executed.

In the `target` folder, a fat JAR is available for the execution.

## How to run
The application is dockerized and can be easily tagged:
```
docker build --tag=shop-place-app .
```
In order to maintain the web service's state, the application performs a connection to a database instance.

A Postgres database is expected. For this purpose, a docker image with Postgres can be launched.

Below you can find the command needed to easily launch the application with a database instance.
```
docker-compose up
```
Data is persistent thanks to volumes. 
On the first launch, the database looks for an init script, that is passed by the `docker-compose` definition: this script will execute the DDLs statements for creating the tables accessed by the application.

## Documentation
If you launch such application locally, it is expected that it will be accessible under `http://localhost:8080/shop-place/` context path.

Indeed, the graphic version of the documentation is available at http://localhost:8080/shop-place/swagger-ui.html

## Authentication
Authentication mechanism is missing. There are several alternatives that can be used. In a Spring application, a framework like [Spring Security](https://spring.io/projects/spring-security) can be a good choice.
It is highly customizable and enables you to add complex authentication and authorization mechanisms, that can be leveraged by a simple annotation.
Going more in details, the JSON Web Token standard could be used together [with Spring Security](https://www.baeldung.com/spring-security-oauth-jwt).

## Redundancy
Redundancy can be easily obtained, by deploying the application container in a Kubernetes cluster. It is important that a load-balancing strategy is in place, in order to distribute the load among all nodes.
Moreover, the same database instance must be reached by all application's replicas. In this way, they will be able to read and write from\to the same data source.

## Assumptions
- When performing a `PUT` for a product, to be robust and non-blocking, the API will consider the `productId` in the URL path as source of truth and ignore the productId, eventually present, in the request body. Such decision is made to avoid blocking situations like 409 HTTP status code, i.e. conflict between current state of the product (its identifier) and the made request content.
- Time period to retrieve the orders is specified by two dates: a start date and an end date (both inclusive). 

  	1) The start date must be before or equals to the end date, otherwise 400 HTTP status code is returned.
  	2) The end date is optional: this means that if not specified, all orders until today would be considered.
- Product price can be negative (maybe a cashback or a promotion). Also for order, product quantity can be negative (return of a product).

## Architectural choices
- Package structure is not following Domain Driven Design. The reason is simple: this application is a tiny CRUD. This is the case told at beginning of [that answer](https://softwareengineering.stackexchange.com/a/365202).
- Due to the nature of the application, the service layer is not performing a lot of business logic. Indeed it is only performing some validation, method calls and objects conversion. From my point of view, it is still important to have it, in order to keep separation of responsibilities and going forward to embrace an hexagonal architecture.
- For the execution of unit tests related to the repository classes, an H2 database in memory is used. A script for the creation of the tables is shared among the "real" Postgres instance and the "fake" H2 one.

## What's next
Generally, I tried to keep the project simple and the dependency layer as small as possible. Here is a list of improvements that come to mind.

- Libraries like [Autovalue](https://github.com/google/auto/blob/master/value/userguide/index.md) and [Lombok](https://projectlombok.org/) can be used to improve code maintainability, for example avoiding to hard-code getters/setters.
- For simplicity, the fat JAR of the application has been included in the docker image. Docker layering can be leveraged by libraries like [Jib](https://github.com/GoogleContainerTools/jib).
- In Postgres, the default schema has been used, even if it is not a best practice. Moreover, no indexes are present and no dedicated tablespace is allocated.
- Repository layer has been implemented with JDBC template. There are a lot of alternatives that avoid to manually write entities SQL queries (e.g. Hibernate). For this reason, repository classes have an interface that can be implemented with a specific ORM (if preferred). `@Qualifier` annotation can be used to load the right implementation at runtime.
