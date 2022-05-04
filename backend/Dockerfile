FROM openjdk:11-jdk

ARG JAR_FILE=ssapilogue/build/libs/ssapilogue-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "/app.jar"]
