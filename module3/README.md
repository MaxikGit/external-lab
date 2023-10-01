# epam-external-course
## module 3

To run module3, you should do next:
1. Create PostgreSQL database, named `module3` (for another name change corresponding property in `data_source.properties`).  
2. Run Postman and import request collections
3. Activate profile `dev` to generate initial data
4. Run `java/com/epam/esm/AdvancedRestApplication.java`
5. Send request [localhost:8080/generate?size=1000&user_has_orders=2]() or use module3_initialize collection in 
Postman to generate initial data<br/>
   1000 users<br/>
   1000 tags<br/>
   10’000 gift certificates <br/>
   with relations

>in dev profile, initial data will be erased on each start of the app, to avoid this, run application 
> with default profile

Files with request collections to test api in postman app
> module3/rest-api-advanced/postman
