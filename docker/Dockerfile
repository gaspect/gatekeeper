FROM base
RUN mkdir /app
WORKDIR /app
ADD ./target/gatekeeper-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]