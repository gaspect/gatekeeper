# Gatekeeper

> Turn any api in a resource server based on OAUTH2 and OpenID Connect protocols.

Very simple and straightforward configuration. You have two environment variables one for the API  that you want to turn into a resource server and other for the JWK( or well know) url of you  authorization server.

- `INTROSPECTION_URI`: The url of the introspection endpoint
- `CLIENT_ID`: The identifier of the gatekeeper client
- `CLIENT_SECRET`: The secret of the gatekeeper client
- `API_URL`: The URL of the api that you want to turn into a resource server

> Note: The server is open through port 8080, can be changed using the environment variable ``PORT`  
