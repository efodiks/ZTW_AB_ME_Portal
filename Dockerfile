FROM maven:3-openjdk-11-slim

#set up app directory
ENV HOME=/home/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

#install dependencies
COPY pom.xml .
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "dependency:go-offline"]

#set up google api credantials file
COPY init_google_api_key_file.sh .
RUN mkdir -p ./secret
RUN ./init_google_api_key_file.sh
ENV GOOGLE_APPLICATION_CREDENTIALS="./secret/portal-google-api.json"

#copy and package source code
COPY . .
RUN ["mvn", "package"]

EXPOSE 8080
ENTRYPOINT ["java","-jar","./target/*.jar"]