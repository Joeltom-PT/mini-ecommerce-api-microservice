FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/api-gateway.jar /app/api-gateway.jar

EXPOSE 8064

ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
