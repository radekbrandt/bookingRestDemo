# bookingRestDemo
Backend and client for REST demo booking system written in Spring (with Spring Boot 2.0)
Uses:
  * H2 in-memory-db
  * Embedded Tomcat


# Backend
Run by executing: mvn clean spring-boot:run

# Console client
Run by executing:
  mvn clean install
  java -jar target\booking-demo-client-0.0.1-SNAPSHOT.jar
  
# Database console
You see database tables using embedded H2 web console. Server (backend) must be running. Open in a browser: 
http://localhost:8080/h2-console
and paste JDBC URL: jdbc:h2:mem:testdb
(user name: sa, password is empty).

