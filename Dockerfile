FROM maven:3-jdk-11-openj9
EXPOSE 50001/tcp
EXPOSE 50001/udp
COPY target/Rent-1.0.jar /app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=container", "/app.jar"]