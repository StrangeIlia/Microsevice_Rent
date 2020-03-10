FROM openjdk:11
ADD target/Rent-1.0.jar rent_service.jar
EXPOSE 50001
ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar", "rent_service.jar"]