FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ./target/OAUTH-SERVER-0.0.1-SNAPSHOT.jar .
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "OAUTH-SERVER-0.0.1-SNAPSHOT.jar"]