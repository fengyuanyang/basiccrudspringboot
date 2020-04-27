# Implementation

### Login
*credential and Roles*
 - admin/admin, ADMIN
 - manager/manager, MANAGER
 - operator/operator, OPERATOR
 

#### Three Roles     
ADMIN: access to all functions.     
MANAGER: modify/delete/view company/client data.     
OPERATOR: create/view company/client data.       

### Company
path ```/company```    
PUT     ```/{id}``` - modifyCompany   
POST    ```/``` - addCompanies    
GET    ```/``` - getCompanies    
GET     ```/{id}``` - get Specific Company   
DELETE     ```/{id}``` - delete Specific Company    

### Client 
path ```/client```    
PUT     ```/{id}``` - modifyClient  
POST    ```/``` - addClients    
GET    ```/``` - getClients    
GET     ```/{id}``` - getSpecific Client    
DELETE     ```/{id}``` - delete Specific Client    

Execute From docker     
```docker run -p 8080:8080 -it fengyuanyang/basiccrudspringboot:1.3 java -jar /springboot-0.0.1-SNAPSHOT.jar```
docker run -p 8080:8080  -it maven:3.6.3-openjdk-8 java -jar /spring/target/springboot-0.0.1-SNAPSHOT.jar
Swagger URL     
http://localhost:8080/swagger-ui.html

