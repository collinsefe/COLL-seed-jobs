#!/bin/bash

# Navigate to the project directory (adjust the path as necessary)
git clone https://gitlab.com/cloud-devops-assignments/spring-boot-react-example.git

cd spring-boot-react-example || exit

ls -la 
sleep 10

# Build the Maven application
echo "Building the application..."
mvn clean install


# Run the Spring Boot application in detached mode
echo "Running the Spring Boot application..."
nohup java -jar target/*.jar --server.port=8081 > application.log 2>&1 &

# Print the PID of the detached process
echo "Application started in detached mode with PID: $!"

# Optionally, you can give the application a moment to start
sleep 10

# Test the API (you may adjust the curl command as necessary)
echo "Testing the API..."
curl -v -u greg:turnquist http://localhost:8082/api/employees/3

while true; do 
    sleep 60
done
