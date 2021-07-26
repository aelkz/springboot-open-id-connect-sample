# [<img src="https://www.keycloak.org/resources/images/keycloak_logo_480x108.png">](https://github.com/aelkz/springboot-open-id-connect-sample)

### Springboot OpenID Connect sample app
##### modules: spring-boot-starter-security, pring-boot-starter-oauth2-resource-server, spring-boot-starter-oauth2-client
##### authorization server: keycloak

1. Start Red Hat Single Sign-On (keycloak)
2. Create a new realm: <b>codigofonte</b>   
3. Under <b>codigofonte</b> realm, create a new user (usr/pwd): <b>john/doe</b>
4. Create a new client under <b>codigofonte</b> realm:
  - <b>product-api</b><br>type: confidential (if using postman to issue new access tokens for testing purposes) or<br>type: bearer-only (real-world scenario)
  <br><br>OBS.: Save the client credentials for use into `application.yaml` file within `security.oauth2.client.registration.keycloak.client-secret` property
    
5. Create new client roles:
  - <b>PRODUCT_VIEWER</b>
  - <b>PRODUCT_MAINTAINER</b>
6. Assign role mappings for user <b>john</b> with recently created client roles
7. Start springboot app on port <b>8070</b>

```shell
rm -fr target ; mvn clean package
java -jar target/springboot-open-id-connect-sample-1.0-SNAPSHOT.jar
```

TEST w/ postman:<br>
`springboot2+spring_security+keycloak.postman_collection.json`

<p align="center">
Acquire access token:
<br>
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/03.png" title="POST /token" width="85%" height="85%" />
<br>
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/04.png" title="POST /token" width="80%" height="80%" />
</p>
<br>
<p align="center">
Create a new product:
<br>
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/01.png" title="POST Product" width="85%" height="85%" />
</p>
<br>
<p align="center">
Retrieve all products:
<br>
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/02.png" title="GET Products" width="85%" height="85%" />
</p>

### API-DOCS (springdoc-openapi)

http://localhost:8070/api-docs (json)<br>
http://localhost:8070/api-docs.yaml

<p align="center">
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/06.png" title="OpenAPI Docs" width="85%" height="85%" />
</p>

### REFERENCES:<br>
https://github.com/thomasdarimont/awesome-keycloak
https://docs.spring.io/spring-security/site/docs/current/reference/html5/<br>
https://github.com/Baeldung/spring-security-oauth<br>
https://www.baeldung.com/spring-security-oauth-jwt<br>
https://github.com/hotire/spring-security-basic<br>
https://stackoverflow.com/questions/58205510/spring-security-mapping-oauth2-claims-with-roles-to-secure-resource-server-endp<br>
https://developers.redhat.com/blog/2020/01/29/api-login-and-jwt-token-generation-using-keycloak
https://access.redhat.com/products/red-hat-single-sign-on
https://www.baeldung.com/spring-rest-openapi-documentation
