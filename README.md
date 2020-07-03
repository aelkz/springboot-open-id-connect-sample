1. Start your RH-SSO / Keycloak
2. Create a new realm: <b>sample</b>
3. Under sample realm, create user: <b>admin/admin</b>
4. Create clients:
  - <b>sample-api</b>
  - <b>sample-web</b>
5. Create client roles (for both clients):
  - <b>PRODUCT_VIEWER</b>
  - <b>PRODUCT_MAINTAINER</b>
6. Create role mappings for user admin with recently created roles
7. Start springboot app on port <b>8070</b>

TEST:<br>
`springboot2+spring_security+keycloak.postman_collection.json`

<p align="center">
Acquire access token:
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/03.png" title="POST /token" width="85%" height="85%" />
<br>
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/04.png" title="POST /token" width="80%" height="80%" />
</p>
<br>
<br>
<br>
<p align="center">
Create a new product:
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/01.png" title="POST Product" width="85%" height="85%" />
</p>
<br>
<p align="center">
Retrieve all products:
<img src="https://raw.githubusercontent.com/aelkz/springboot-open-id-connect-sample/master/images/02.png" title="GET Products" width="85%" height="85%" />
</p>

REFERENCES:<br>
https://docs.spring.io/spring-security/site/docs/current/reference/html5/<br>
https://github.com/Baeldung/spring-security-oauth<br>
https://www.baeldung.com/spring-security-oauth-jwt<br>
https://github.com/hotire/spring-security-basic<br>
https://stackoverflow.com/questions/58205510/spring-security-mapping-oauth2-claims-with-roles-to-secure-resource-server-endp<br>
