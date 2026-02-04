FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ./target/COURSE-SERVICE-0.0.1-SNAPSHOT.jar .
EXPOSE 9002

ENTRYPOINT ["java", "-jar", "COURSE-SERVICE-0.0.1-SNAPSHOT.jar"]