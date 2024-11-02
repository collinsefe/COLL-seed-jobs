#!/bin/bash

# Navigate to the project directory (adjust the path as necessary)

ls -la 
sleep 10


# Build the Maven application
echo "Building the application..."
mvn clean install

# Check if the build was successful
if [ $? -ne 0 ]; then
    echo "Maven build failed. Exiting..."
    exit 1
fi

# Run the Spring Boot application
echo "Running the Spring Boot application..."
java -jar target/*.jar &

# Store the PID of the Java process
APP_PID=$!

# Give the application a moment to start
sleep 10

# Test the API (you may adjust the curl command as necessary)
echo "Testing the API..."
curl -v -u greg:turnquist http://localhost:8080/api/employees/3

# Optionally, you could wait for the user to exit the application
# echo "Press [CTRL+C] to stop the application."
# wait $APP_PID

# If you want to stop the application on exit
trap "kill $APP_PID" EXIT

# Keep the script running while the application is running
wait $APP_PID
