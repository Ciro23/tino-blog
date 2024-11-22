FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

COPY src/main/resources/certs ./src/main/resources/certs

RUN mvn clean package

RUN mv target/tino-blog-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java", "-jar", "app.war"]
