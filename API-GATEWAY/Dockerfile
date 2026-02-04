FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ./target/API-GATEWAY-0.0.1-SNAPSHOT.jar .
EXPOSE 9002

ENTRYPOINT ["java", "-jar", "API-GATEWAY-0.0.1-SNAPSHOT.jar"]