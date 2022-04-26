FROM openjdk:11-jdk

EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "/app.jar"]