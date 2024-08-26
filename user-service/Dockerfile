FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/user-service.jar /app/user-service.jar

EXPOSE 8062

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
