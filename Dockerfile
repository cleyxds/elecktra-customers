FROM openjdk:11-jre-slim

ENV ROOTPATH=/home/application

WORKDIR ${ROOTPATH}

VOLUME ${ROOTPATH}/database
VOLUME ${ROOTPATH}/images

COPY target/springcustomers-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "springcustomers-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
