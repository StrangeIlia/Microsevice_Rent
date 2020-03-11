FROM maven:3-jdk-11-openj9
# image layer
WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 50001
ADD ./target/your.jar /developments/
ENTRYPOINT ["java","-jar","/developments/your.jar"]