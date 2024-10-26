FROM openjdk:23-jdk-slim

WORKDIR /app

COPY out/artifacts/zcom_back_gate_jar/zcom_back_gate.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
