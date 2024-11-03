#!/bin/bash

# Navigate to the project directory (adjust the path as necessary)
git clone  https://gitlab.com/blue-harvest-assignments/cloud-assignment.git

cd cloud-assignment || exit

#  stage: build
dotnet restore
dotnet build --configuration Release

#stage: test
dotnet test

#stage: publish
dotnet publish -c Release -o out

docker build -t cap-gem-app .
docker run -d -p 8080:8082 cap-gem-app

