# Etapa de construcción
FROM amazoncorretto:17 AS build

# Instalar wget, tar y gzip (necesarios para descargar y extraer Maven)
RUN yum install -y wget tar gzip

# Descargar e instalar una versión más reciente de Maven
RUN wget https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \
    tar -xvzf apache-maven-3.8.6-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.6/bin/mvn /usr/local/bin/mvn

# Verificar la versión de Maven instalada
RUN mvn -v

# Copia el código fuente al contenedor
COPY . /app
WORKDIR /app

# Limpiar el proyecto y construir el JAR
RUN mvn clean package -DskipTests

# Etapa final
FROM amazoncorretto:17

# Copia el archivo JAR de la etapa de construcción
COPY --from=build /app/target/*.jar application.jar

# Comando de entrada para ejecutar la aplicación Java
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]