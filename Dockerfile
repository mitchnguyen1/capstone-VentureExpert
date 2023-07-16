#
# Build stage
#
FROM maven:3.8.7-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:18.0.2.1-jdk-slim-buster
COPY --from=build /target/capstone-0.0.1-SNAPSHOT.jar /app/demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
