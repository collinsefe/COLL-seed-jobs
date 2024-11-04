#!/bin/bash

DOCKER_HUB_USERNAME="collinsefe"  
DOCKER_HUB_PASSWORD=credentials('collinsefe-dockerhub')  
IMAGE_NAME="${DOCKER_HUB_USERNAME}/dotnet-app-image"  
TAG="latest" 
cd aspnet-core-dotnet-core

ls -la 

echo "Building Docker image..."
sudo docker build -t "${IMAGE_NAME}:${TAG}" .

# Check if the build was successful
if [ $? -ne 0 ]; then
    echo "Docker build failed. Exiting..."
    exit 1
fi

echo "Logging in to Docker Hub..."

sudo docker login -u collinsefe -p "${DOCKER_HUB_PASSWORD}"

# Check if login was successful
if [ $? -ne 0 ]; then
    echo "Docker login failed. Exiting..."
    exit 1
fi

echo "Pushing Docker image to Docker Hub..."
sudo docker push "${IMAGE_NAME}:${TAG}"

# Check if the push was successful
if [ $? -ne 0 ]; then
    echo "Docker push failed. Exiting..."
    exit 1
fi

echo "Cleaning up local Docker image..."
sudo docker rmi "${IMAGE_NAME}:${TAG}"

echo "Docker image has been pushed successfully!"
