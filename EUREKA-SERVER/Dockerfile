FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ./target/EUREKA-SERVER-0.0.1-SNAPSHOT.jar .
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "EUREKA-SERVER-0.0.1-SNAPSHOT.jar"]