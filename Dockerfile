FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

# Pre-download dependencies and compile the project
RUN mvn -B -q -DskipTests dependency:go-offline
RUN mvn -B -q -DskipTests package

# Run tests during image build to validate the project
RUN mvn -B -q test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /workspace

COPY --from=builder /workspace/target /workspace/target

# Default command to run tests in the container
ENTRYPOINT ["mvn", "-B", "test"]
