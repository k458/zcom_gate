FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/zcom_back_gate-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
