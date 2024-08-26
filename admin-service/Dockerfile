FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/admin-service.jar /app/admin-service.jar

EXPOSE 8060

ENTRYPOINT ["java", "-jar", "/app/admin-service.jar"]
