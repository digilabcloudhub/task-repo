FROM openjdk:8-jdk-alpine
COPY target/atm-0.0.1-SNAPSHOT.jar atm-services-1.0.0.jar
ENTRYPOINT ["java","-jar","/atm-services-1.0.0.jar"]