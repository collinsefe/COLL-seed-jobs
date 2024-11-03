#!/bin/bash

# Navigate to the project directory (adjust the path as necessary)
git clone  https://gitlab.com/blue-harvest-assignments/cloud-assignment.git

cd cloud-assignment || exit

docker build -t cap-gem-app .
docker run -d -p 8080:80 your-image-name

 # Install dependencies
npm install

# Build the frontend
npm run build

# Deploy to specified location or server
npm run deploy
