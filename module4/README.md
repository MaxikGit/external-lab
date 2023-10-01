# epam-external-course
## module 4

To run module4, you should do next:
1. Create PostgreSQL databases, named `module4` and `module4_auth`
2. Run Postman and import request collections
3. Activate profile `dev` when starting resource-server app to generate initial data
4. Run `java/com/epam/esm/AuthorizationServerApplication.java` and `java/com/epam/esm/ResourceServerApp.java`
5. Use `initialize_module4` collection in 
Postman to send request & generate initial data<br/>
   200 tags<br/>
   300 gift certificates <br/>.
6. Use `module4_auth_server/registration` collection in
   Postman to send request & generate first user
7. Send request `localhost:9000/oauth2/authorize?response_type=code&client_id=client&redirect_uri=https://spring.io/auth`
8. Copy code in the server's response and use it in `module4_auth_server/retreive access token by code` collection in
   Postman to send request & receive Bearer token

Files with request collections to test api in postman app
> module4/postman