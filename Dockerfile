# ---- Build stage ----
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user to run the service
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/build/libs/*.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]
