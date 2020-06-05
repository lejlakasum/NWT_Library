FROM openjdk:8
EXPOSE 8090
ADD target/gateway-jar.jar gateway-jar.jar
ENTRYPOINT ["java", "-jar", "/gateway-jar.jar"]