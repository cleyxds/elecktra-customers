FROM openjdk:11-jre-slim

WORKDIR home/application

COPY target/sqlite-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "sqlite-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
