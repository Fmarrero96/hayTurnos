# Utiliza una imagen base de OpenJDK 17 con Alpine
FROM maven:3.8.1-openjdk-17 AS builder

# Establece el directorio de trabajo en la aplicación
WORKDIR /app

# Copia el archivo pom.xml para descargar las dependencias
COPY pom.xml .

# Copia los archivos del código fuente a la imagen
COPY src ./src

# Ejecuta Maven para construir el proyecto
RUN mvn clean package

# Cambia a una imagen más ligera para la ejecución
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en la aplicación
WORKDIR /app

# Copia el archivo WAR generado en la fase anterior
COPY --from=builder /app/target/hayTurnos-0.0.1-SNAPSHOT.war java-app.war

# Indica el comando para ejecutar la aplicación cuando se inicia el contenedor
ENTRYPOINT ["java", "-jar", "java-app.war"]