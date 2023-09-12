
# Spring Boot Newspaper API

# Database
Using PostgreSQL:
- Development mode: localhost
- Production mode: hosting on **Supabase**

# How to run
- Open terminal and enter this command: *mvn spring-boot:run -Dspring-boot.run.profiles=****
- Or edit configuration of IDE with Maven type and add this command: *spring-boot:run -Dspring-boot.run.profiles=****

# How to build
- Open terminal and enter this command: *mvn clean package -P****
- Or edit configuration of IDE with Maven type and add this command: *clean package -P****

Replace *** with **dev** or **prod** to run with **development** or **production** environment

# Deployment
- Development API can be accessed at: **http://localhost:8080/swagger-ui**
- Production API is deployed to **Render** at: **https://meu-newspaper.onrender.com/swagger-ui**

