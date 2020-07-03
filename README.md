1. Start your RH-SSO / Keycloak
2. Create user admin/admin
3. Create clients:
  - sample-api
  - sample-web
4. Create client roles (both clients):
  - ROLE_PRODUCT_VIEWER
  - ROLE_PRODUCT_MAINTAINER
5. Start springboot app on port 8070

TEST:
springboot2+spring_security+keycloak.postman_collection.json

REFERENCES:
https://docs.spring.io/spring-security/site/docs/current/reference/html5/
https://github.com/Baeldung/spring-security-oauth
https://www.baeldung.com/spring-security-oauth-jwt
https://github.com/hotire/spring-security-basic
https://stackoverflow.com/questions/58205510/spring-security-mapping-oauth2-claims-with-roles-to-secure-resource-server-endp
