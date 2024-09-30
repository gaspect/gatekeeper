# Gatekeeper

> Turn any api in a resource server based on OAUTH2 and OpenID Connect protocols.

Very simple and straightforward configuration. You have two environment variables one for the API  that you want to turn into a resource server and other for the JWK( or well know) url of you  authorization server.

- `INTROSPECTION_URI`: The url of the introspection endpoint
- `CLIENT_ID`: The identifier of the gatekeeper client
- `CLIENT_SECRET`: The secret of the gatekeeper client
- `API_URL`: The URL of the api that you want to turn into a resource server

> Note: The server is open through port 8080, can be changed using the environment variable ``PORT`  

## Handle multiples APIS with one gateway

To handle multiples API a fine grained configuration must be provided. Just read the current configuration as an example:

```yml
spring:
    application:
        name: gateway
    security:
        oauth2:
            resourceserver:
                opaquetoken:
                    introspection-uri: ${INTROSPECTION_URI}
                    client-id: ${CLIENT_ID}
                    client-secret: ${CLIENT_SECRET}

    cloud:
        gateway:
            routes:
            - id: main
              uri: ${API_URI}
              predicates:
                - Path=/**

server:
        port: ${PORT:8080}
```

Adding routes to gateway property should solve handling multiple API. Then your configuration file must be mounted in the gatekeeper container to run a command for spring initialization with the custom configuration.

```yml
name: "gatekeeper"
services:
  gatekeeper:
    image: jesusefg/gatekeeper:latest
    command: ["java ", "-jar", "/app/gatekeeper.jar", "--spring.config.location=/app"]
    volumes:
      - './custom-configuration.yml:/app/application.yml'
```
