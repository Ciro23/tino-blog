FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

COPY src/main/resources/certs ./src/main/resources/certs

RUN mvn clean package

RUN mv target/tino-blog-1.3.2.war app.war
ENTRYPOINT ["java", "-jar", "app.war"]
