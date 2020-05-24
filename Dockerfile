FROM maven:3-openjdk-11-slim

#set up app directory
ENV HOME=/home/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

#install dependencies
COPY pom.xml .
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "dependency:go-offline"]

#copy and package source code
COPY . .
RUN ["mvn", "package"]

EXPOSE 8080
ENTRYPOINT java -jar ./target/*.jar