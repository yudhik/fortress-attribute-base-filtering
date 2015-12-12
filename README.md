# fortress-attribute-base-filtering
Prove Of Concept to filtering attribute base using apache fortress

Step by step configuration :
1. change fortress.properties configuration (src/main/resources/fortress.properties)
2. run your fortress
3. run this application and you will see blank demo1.jsp
4. register permission object with your page as object name (naming convention will be `page`#`object.attribute`) and operation name (naming convention will be `page`^filter) through commander 
5. register permissions and link it to your prefer role name
6. link user (at your fortress.properties) with role
7. refresh the page.


To run application :
> mvn clean install && mvn jetty:run -Dversion=fortress-version


