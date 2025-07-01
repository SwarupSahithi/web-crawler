# ---------- Stage 1: Build JAR ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside container
WORKDIR /build

# Copy project files to the container
COPY . .

# Build the project using Maven
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run the app ----------
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the shaded JAR from the builder stage
COPY --from=builder /build/target/web-crawler-1.0-SNAPSHOT-shaded.jar app.jar

# Entry point
ENTRYPOINT ["java", "-jar", "app.jar"]
