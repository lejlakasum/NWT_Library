FROM openjdk:8
EXPOSE 8081
ADD target/user-jar.jar user-jar.jar
ENTRYPOINT ["java", "-jar", "/user-jar.jar"]