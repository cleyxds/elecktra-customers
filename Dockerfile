FROM openjdk:11-jre-slim

ENV DIRPATH=/home/application

WORKDIR ${DIRPATH}

VOLUME ${DIRPATH}/database
VOLUME ${DIRPATH}/images

COPY target/sqlite-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "sqlite-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
