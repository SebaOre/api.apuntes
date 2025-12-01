# Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Final
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
ENV MONGO_URI=${MONGO_URI}
ENV SPRING_DATA_MONGODB_URI=${MONGO_URI}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
