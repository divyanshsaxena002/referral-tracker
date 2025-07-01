# Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-20 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lightweight JRE to run the application
FROM eclipse-temurin:20-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"] 