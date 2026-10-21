# syntax=docker/dockerfile:1.7

# Cache Maven dependencies separately from application source.
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -DskipTests dependency:go-offline
COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
RUN groupadd --system spring && useradd --system --gid spring spring
COPY --from=build /workspace/target/*.jar app.jar
USER spring:spring
EXPOSE 8082
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
