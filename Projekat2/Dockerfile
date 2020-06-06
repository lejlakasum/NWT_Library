FROM openjdk:8
EXPOSE 8082
ADD target/book-jar.jar book-jar.jar
ENTRYPOINT ["java", "-jar", "/book-jar.jar"]