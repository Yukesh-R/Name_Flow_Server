FROM maven:3.9.8-amazoncorretto-21
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT  ["java","-jar","/app.jar"]