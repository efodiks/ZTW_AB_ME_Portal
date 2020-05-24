FROM maven:3-openjdk-11-slim

#set up args

ARG auth_provider_x509_cert_url
ARG auth_uri
ARG client_email
ARG client_id
ARG client_x509_cert_url
ARG private_key
ARG private_key_id
ARG project_id
ARG token_uri
ARG type

#set up env
ENV auth_provider_x509_cert_url $auth_provider_x509_cert_url
ENV auth_uri $auth_uri
ENV client_email $client_email
ENV client_id $client_id
ENV client_x509_cert_url $client_x509_cert_url
ENV private_key $private_key
ENV private_key_id $private_key_id
ENV project_id $project_id
ENV token_uri $token_uri
ENV type $type

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