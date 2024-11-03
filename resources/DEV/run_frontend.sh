#!/bin/bash

sudo docker pull collinsefe/dotnet-app-image
sudo docker run -d -p 8080:8082 collinsefe/dotnet-app-image

