FROM openjdk:8
EXPOSE 8083
ADD target/analytics-jar.jar analytics-jar.jar
ENTRYPOINT ["java", "-jar", "/analytics-jar.jar"]