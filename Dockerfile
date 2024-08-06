FROM eclipse-temurin:21-jre
LABEL maintainer="Jesús Enrique Fuentes González <jesusefg12@gmail.com>"

RUN mkdir /app
WORKDIR /app
ADD ./target/gatekeeper-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java","-jar","./app.jar"]