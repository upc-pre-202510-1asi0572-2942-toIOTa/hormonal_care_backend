# Hormonal Care Backend

This is the backend application for the Hormonal Care system, built with Spring Boot 3, MySQL, and MongoDB.

## Prerequisites

- Docker and Docker Compose installed on your system
- Java 16 or later (for local development outside Docker)

## Docker Commands

### Start the application

To start the application with all required services (MySQL and MongoDB):
```sh
docker-compose up -d
```

Check running containers
```sh
docker-compose ps
```

Stop all services without removing containers:
```sh
docker-compose stop
```

Start previously stopped services:
```sh
docker-compose start
```

Remove containers but keep volumes:
```sh
docker-compose down
```

### Maven Commands for Starting the Application
Using Global Maven Installation
```sh
mvn spring-boot:run
```



### Connections

#### Database Connection Information MySQL
```
Port: 3377 (mapped from standard 3306)

Username: root

Password: root

Database: hormonal

Connection URL: jdbc:mysql://localhost:3377/hormonal
```

#### MongoDB
```
Port: 27027 (mapped from standard 27017)

Database: hormonal_care_communication

Connection URL: mongodb://localhost:27027/hormonal_care_communication
```

### Swagger UI

Enter to this link: http://localhost:8080/swagger-ui/index.html#/