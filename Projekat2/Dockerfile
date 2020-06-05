FROM openjdk:8
EXPOSE 8081
ADD target/book-jar.jar book-jar.jar
ENTRYPOINT ["java", "-jar", "/book-jar.jar"]