# ===============================
# Etapa 1: Build de la aplicación
# ===============================
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY . .

# Construir el jar
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# ===============================
# Etapa 2: Imagen final liviana
# ===============================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar JAR construido
COPY --from=build /app/target/*.jar app.jar

# Render usará la variable PORT, así que Spring debe escuchar ese puerto
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
