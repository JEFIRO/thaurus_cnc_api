# Etapa 1: Build do app com Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime com Oracle JDK 17
FROM ghcr.io/graalvm/jdk-community:17
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080
ports:
  - "9090:8080"


# Executa o app
ENTRYPOINT ["java", "-jar", "app.jar"]
