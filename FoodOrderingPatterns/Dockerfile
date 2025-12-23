# Stage 1: Compile all Java sources
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy EVERY .java file from the backend directory
COPY backend/*.java ./

# Compile all of them together
RUN javac *.java

# Stage 2: Minimal runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy all compiled .class files
COPY --from=builder /app/*.class ./

EXPOSE 9999

# Run the server
CMD ["java", "SimpleServer"]