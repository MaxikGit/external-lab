# epam-external-course
## module 2

The application is using external tomcat 9 (9.0.65) server with next deployment configuration </br>
(as it used in postman collections):
1. no application context
2. localhost
3. port: 8080

To run module2, you should do next:
1. create and configure PostgreSQL database (name used in project - 'module2')
2. `cd module2/rest-api-basics`
3. in terminal: `./gradlew war`
4. generated war-file should be placed into `\webapps\` directory of Tomcat9
5. delete `/ROOT/` directory (if exist)
6. rename `file-name.war` into `ROOT.war`
7. run `/bin/startup.bat`
8. run Postman and import request collections

File to create and fill database:
> module2/rest-api-basics/src/main/sql/CreateAndFillOut.sql

Files with request collections to test api in postman app
> module2/rest-api-basics/postman
