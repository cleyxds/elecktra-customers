FROM maven:3.5-jdk-11 AS build

LABEL author="cleyxds"

COPY src /home/customers/src
COPY pom.xml /home/customers

RUN mvn -f /home/customers/pom.xml clean package -D skipTests

#Need to provide a PersistentVolumeClaims or a Docker Volume to the path=/database

FROM openjdk:11-jre-slim

COPY --from=build /home/customers/target/*.jar /home/customers/customers.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/customers/customers.jar"]
