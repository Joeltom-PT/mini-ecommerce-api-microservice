FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/product-service.jar /app/product-service.jar

EXPOSE 8061

ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]
