FROM maven:3.9.5-amazoncorretto-17
COPY . ./app
WORKDIR app
RUN mvn clean install
EXPOSE 5050
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/taskmanagement-0.0.1-SNAPSHOT.jar"]

