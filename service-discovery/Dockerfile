FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/service-discovery.jar /app/service-discovery.jar

EXPOSE 8065

ENTRYPOINT ["java", "-jar", "/app/service-discovery.jar"]
