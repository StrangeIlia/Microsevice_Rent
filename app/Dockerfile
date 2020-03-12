# Set ubuntu 18.04 as base image
FROM ubuntu:18.04

# Install updates
RUN apt-get update && apt-get -y install software-properties-common

# Install wget, curl, vim
RUN apt-get -y install wget curl vim

# Install Java 11
RUN cd /opt; \
    wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/11.0.1+13/90cf5d8f270a4347a95050320eef3fb7/jdk-11.0.1_linux-x64_bin.tar.gz \
    && tar zxf jdk-11.0.1_linux-x64_bin.tar.gz \
    && ln -s jdk-11 java \
    && rm -f jdk-11.0.1_linux-x64_bin.tar.gz

# Set JAVA_HOME and add it to environment PATH
ENV JAVA_HOME=/opt/jdk-11.0.1
ENV PATH="$PATH:$JAVA_HOME/bin"

# Install Maven
RUN apt-get -y install maven

# Copy sample-maven-app to user directory
COPY . /app

# Maven install and assemble sample app
RUN cd /app && mvn clean spring-boot:install -Dspring-boot.profiles=docker