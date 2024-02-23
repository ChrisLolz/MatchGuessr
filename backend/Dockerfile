FROM openjdk:17-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/backend-0.0.1.jar
ADD ${JAR_FILE} backend.jar
ENTRYPOINT [ "java", "-jar", "/backend.jar" ]