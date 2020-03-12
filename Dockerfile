FROM maven:3-jdk-11-openj9
COPY target/Rent-1.0.jar /app.jar
EXPOSE 50001
CMD ["java", "-jar", "-Dspring.profiles.active=container", "/app.jar"]