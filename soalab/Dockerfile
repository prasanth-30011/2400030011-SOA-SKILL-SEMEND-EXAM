FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/app-service-0.0.1-SNAPSHOT.jar app-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app-service.jar"]
