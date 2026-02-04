FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ./target/USER-SERVICE-0.0.1-SNAPSHOT.jar .
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "USER-SERVICE-0.0.1-SNAPSHOT.jar"]