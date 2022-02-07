FROM openjdk:17
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/tots-api-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT java -jar tots-api-0.0.1-SNAPSHOT.jar