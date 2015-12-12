# fortress-attribute-base-filtering
Prove Of Concept to filtering attribute base using apache fortress

Step by step configuration :  
1. change fortress.properties configuration (src/main/resources/fortress.properties)  
2. run your fortress  
3. run this application and you will see blank demo1.jsp  
![alt tag](https://raw.github.com/yudhik/fortress-attribute-base-filtering/master/fortress-example-demo/before-role-register.JPG)  
4. register permission object with your page as object name (naming convention will be `page`#`object.attribute`) 
![alt tag](https://raw.github.com/yudhik/fortress-attribute-base-filtering/master/fortress-example-demo/create-permission-object.JPG)  
5. register permissions with operation name (naming convention will be `page`^filter) and link it to your prefer role name  
![alt tag](https://raw.github.com/yudhik/fortress-attribute-base-filtering/master/fortress-example-demo/assign-roles-to-permission-obj.JPG)  
6. link user (at your fortress.properties) with role  
![alt tag](https://raw.github.com/yudhik/fortress-attribute-base-filtering/master/fortress-example-demo/assign-roles-to-user.JPG)  
7. refresh the page.  
![alt tag](https://raw.github.com/yudhik/fortress-attribute-base-filtering/master/fortress-example-demo/after-role-registered.JPG)  


To run application :  
> mvn clean install && mvn jetty:run -Dversion=fortress-version


