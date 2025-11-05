FROM maven:3.9.11-eclipse-temurin-25 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

COPY src/main/resources/certs ./src/main/resources/certs

RUN mvn clean package

RUN mv target/tino-blog-1.3.4.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
