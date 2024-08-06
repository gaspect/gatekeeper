# Gatekeeper

> Turn any api in a resource server based on OAUTH2 and OpenID Connect protocols.

Very simple and straightforward configuration. You have two environment variables one for the API  that you want to turn into a resource server and other for the JWK( or well know) url of you  authorization server.

- `JWK_URI`: The url of the well know configuration of the authorization server.
- `API_URL`: The URL of the api that you want to turn into a resource server

This is a simple configuration example:

```yml
name: 'gatekeeper-example'


services:
  edge:
    image: traefik:latest
    command:
      - "--api.insecure=true"
      - "--providers.docker=true"
      - "--providers.docker.exposedbydefault=false"
      - "--log.level=DEBUG"
    ports:
      - "80:80"
      - "8080:8080"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

  oauthdb:
    image: postgres:alpine3.20
    volumes:
      - ./data/keycloak:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: keycloak
      POSTGRES_USER: keycloak
      POSTGRES_PASSWORD: keycloak
    healthcheck:
      test: "exit 0"

  iam:
    image: keycloak/keycloak:latest
    command: ["start-dev", "--hostname-strict", "false", "--proxy-headers", "xforwarded", "--http-relative-path", "/iam/"]
    environment:
      KC_DB: postgres
      KC_DB_URL_HOST: oauthdb
      KC_DB_URL_DATABASE: keycloak
      KC_DB_PASSWORD: keycloak
      KC_DB_USERNAME: keycloak
      KC_DB_SCHEMA: public
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: password
      PROXY_ADDRESS_FORWARDING: true
    depends_on:
      oauthdb:
        condition: service_healthy
    labels:
      - "traefik.enable=true"
      - "traefik.http.services.iam.loadbalancer.server.port=8080"
      - "traefik.http.routers.iam.rule=Host(`gatekeeper.ex.localhost`) && PathPrefix(`/iam`)"

  whoami-gatekeeper:
    image: gatekeeper:latest 
    build: .
    environment: 
      - JWK_URI=http://iam:8080/iam/realms/master/.well-known/openid-configuration
      - API_URI=http://whoami:8080
    depends_on:
      - iam
    labels:
        - "traefik.enable=true"
        - "traefik.http.services.pacs.loadbalancer.server.port=8080"
        - "traefik.http.routers.pacs.rule=Host(`gatekeeper.ex.localhost`)"
    
  whoami:
    image: traefik/whoami:latest
```

If you access to <http://gatekeeper.ex.localhost> directly from the browser then you will see a 401
unauthorized error message because the authorization token isn't provided.You must create a user in [KeyCloak](http://gatekeeper.ex.localhost/iam) and use [Postman](https://www.postman.com/downloads/) or any related Api Development Tool to get a valid token and do the right request.
