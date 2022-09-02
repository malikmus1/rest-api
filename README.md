Restful CRUD API using Spring Boot, Mysql, JPA and Hibernate.

### Requirements

Java - 1.8.x, Maven, Mysql 

### Setup

**1. Create database**
```bash
create database <databse name>
```

**2. Change database config**

+ in `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` 
+ change `spring.datasource.url` according to your database name. 

**3. Build and run the app with maven**

You can run the app with

```bash
mvn spring-boot:run
```
or run it directly from your IDE. 

### All Rest APIs

CRUD APIs: 

**1. Director**

    GET /directors
    POST /directors
    GET /directors/{directorId}
    PUT /directors/{directorId}
    DELETE /directors/{directorId}
    
 **2. Movie**
  
    GET /movies
    POST /movies
    GET /movies/{movieId}
    PUT /movies/{movieId}
    DELETE /movies/{movieId}
   
 **3. Directors and Movies**
 
    PUT /directors/{directorId}/movies
    DELETE /directors/{direcorId}/movies
    PUT /movies/{movieId}/directors
    DELETE /movies/{movieId}/directors
   

You can test them using postman or any other rest client.
