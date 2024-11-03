#!/bin/bash

# Variables

DOCKER_HUB_USERNAME="collinsefe"  # Replace with your Docker Hub username
DOCKER_HUB_PASSWORD="${collins}"  # Replace with your Docker Hub password
IMAGE_NAME="${DOCKER_HUB_USERNAME}/dotnet-app-image"  # Replace with your image name
TAG="latest"  # or use the build number as a tag

# Navigate to the project directory (adjust the path as necessary)

# Replace with the actual path to your project

cd aspnet-core-dotnet-core

ls -la 


# Checkout the source code (if needed)
# git clone https://gitlab.com/your-repo.git  # Uncomment if you need to clone the repo
# cd your-repo  # Change to the repo directory

# Build Docker image
echo "Building Docker image..."
sudo docker build -t "${IMAGE_NAME}:${TAG}" .

# Check if the build was successful
if [ $? -ne 0 ]; then
    echo "Docker build failed. Exiting..."
    exit 1
fi

# Login to Docker Hub
echo "Logging in to Docker Hub..."
#echo "${collinsefe}" | docker login -u "${DOCKER_HUB_USERNAME}" --password-stdin

sudo docker login -u collinsefe -p "${DOCKER_HUB_PASSWORD}"

# Check if login was successful
if [ $? -ne 0 ]; then
    echo "Docker login failed. Exiting..."
    exit 1
fi

# Push Docker image to Docker Hub
echo "Pushing Docker image to Docker Hub..."
sudo docker push "${IMAGE_NAME}:${TAG}"

# Check if the push was successful
if [ $? -ne 0 ]; then
    echo "Docker push failed. Exiting..."
    exit 1
fi

# Cleanup: remove the local Docker image
echo "Cleaning up local Docker image..."
sudo docker rmi "${IMAGE_NAME}:${TAG}"

echo "Docker image has been pushed successfully!"
