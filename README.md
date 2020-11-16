# Java Challenge

The following technologies were used in this project:
- Java 8
- Gradle
- Lombok
- Swagger
- DataFactory
- Spring Boot
  * Jpa
  * Web
  * Actuator
  * Devtools
- PostgreSQL
- Flyway

## Requirements
- Java 8
- Gradle
- PostgreSQL
  * It is necessary to have the _postgres_ database created	
  * To configure access to the database, you must modify the _application.properties_ file in the resources directory

## Health
The health was added to be able to verify the status of the api

##### Check the status of the api
##### GET
```bash
/api/challenge/actuator/health
```

## Documentation
You can get more information about each one looking at the Swagger documentation: 
```bash
/api/challenge/swagger-ui.html
```
