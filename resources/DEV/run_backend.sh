# #!/bin/bash

# # Navigate to the project directory (adjust the path as necessary)
# git clone https://gitlab.com/cloud-devops-assignments/spring-boot-react-example.git

# cd spring-boot-react-example || exit

ls -la 

echo "Building the application..."
mvn clean install


 echo "Running the Spring Boot application..."
 nohup java -jar target/*.jar --server.port=${port} > application.log 2>&1 &

echo "Application started in detached mode with PID: $!"

sleep 10

# echo "Testing the API..."
curl -v -u greg:turnquist http://localhost:8081/api/employees/3

# while true; do 
#     sleep 60
# done
