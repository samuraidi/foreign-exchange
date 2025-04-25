# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
# ✅ This one is officially published

WORKDIR /fxapi
COPY . .
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:17-jdk
# ✅ Also verified

WORKDIR /fxapi
COPY --from=build /fxapi/target/fxapi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]



