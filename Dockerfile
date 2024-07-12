# Stage 1: Build the application
FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Run the application
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/taskmanagement-0.0.1-SNAPSHOT.jar /app/taskmanagement.jar
EXPOSE 5050
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/taskmanagement.jar"]
