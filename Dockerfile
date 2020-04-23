FROM adoptopenjdk/openjdk11:alpine

WORKDIR ./app

COPY .mvn ./.mvn
COPY mvnw ./
COPY pom.xml ./
COPY src ./src

RUN sh ./mvnw clean install

ARG JAR_FILE=target/*.jar

RUN mv ${JAR_FILE} ./target/app.jar

ENTRYPOINT ["java","-jar","/app/target/app.jar"]