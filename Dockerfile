FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/ccsmark_consumer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar", "/app.jar"]