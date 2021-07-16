FROM openjdk:11-jre-slim

MAINTAINER cleyxds

WORKDIR /home/application

#Need to provide a PersistentVolumeClaims or a Docker Volume to the path=/home/application/database

COPY target/springcustomers-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "springcustomers-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
